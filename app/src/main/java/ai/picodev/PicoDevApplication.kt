package ai.picodev

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.util.Log
import android.widget.Toast
import ai.picodev.data.adb.AdbConnectManager
import ai.picodev.data.updater.PluginUpdateCheckWorker
import ai.picodev.data.updater.UpdateCheckWorker
import com.telemetrydeck.sdk.Signal
import com.telemetrydeck.sdk.SignalCache
import com.telemetrydeck.sdk.TelemetryDeck
import com.telemetrydeck.sdk.TelemetryDeckSessionManagerProvider
import com.telemetrydeck.sdk.TelemetryDeckSignalProcessor
import java.io.File
import java.security.MessageDigest
import java.util.UUID
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import ai.picodev.i18n.L

class PicoDevApplication : Application(), coil.ImageLoaderFactory {
    /**
     * Images that live on the bridge (a server's files, attachments) need the security header, which Coil's
     * default client does not send. Only requests to the bridge address get it.
     */
    override fun newImageLoader(): coil.ImageLoader {
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor { chain ->
                val req = chain.request()
                val bridge = ai.picodev.data.preferences.AuthPreferences.currentBridgeHttpUrl.toHttpUrlOrNull()
                val sameServer = bridge != null && req.url.host == bridge.host && req.url.port == bridge.port
                chain.proceed(
                    if (sameServer) req.newBuilder()
                        .header(ai.picodev.data.preferences.AuthPreferences.currentFramedHeader, "true")
                        .apply { ai.picodev.data.preferences.AuthPreferences.edgeHeader()?.let { (n, v) -> header(n, v) } }
                        .build()
                    else req
                )
            }
            .build()
        return coil.ImageLoader.Builder(this).okHttpClient(client).build()
    }

    override fun onCreate() {
        super.onCreate()
        // a crash opens the crash screen (with the report) instead of the app just vanishing
        ai.picodev.crash.CrashHandler.install(this)
        // the crash screen's own process needs none of the startup below
        if (ai.picodev.crash.CrashHandler.isCrashProcess(this)) return
        // One broken subsystem must not stop the app before its first screen: log and skip that initializer.
        safeInit("AuthPreferences") { ai.picodev.data.preferences.AuthPreferences(this) }
        safeInit("ThemeManager") { ai.picodev.theme.ThemeManager.init(this) }
        safeInit("LanguageManager") { ai.picodev.i18n.LanguageManager.init(this) }
        safeInit("UpdateCheckWorker") { UpdateCheckWorker.schedule(this) }
        safeInit("PluginUpdateCheckWorker") { PluginUpdateCheckWorker.schedule(this) }
        safeInit("MiniAppScheduler") { ai.picodev.data.miniapps.MiniAppScheduler.rearmAsync(this) }
        safeInit("MiniAppShortcuts") { Thread { ai.picodev.ui.miniapps.MiniAppShortcuts.migrate(this) }.start() }
        safeInit("AdbConnectManager") { AdbConnectManager.init(this) }
        safeInit("DeviceAccess") { ai.picodev.data.adb.DeviceAccess.init(this) }
        safeInit("ChrootManager") { ai.picodev.data.local.chroot.ChrootManager.init(this) }
        safeInit("SshSessionFactory") { ai.picodev.data.ssh.SshSessionFactory.init(this) }
        safeInit("PcConnect") { ai.picodev.data.pc.PcConnect.init(this) }
        safeInit("AgyCloud") { ai.picodev.data.agent.agy.AgyCloud.init(this) }
        // Forks build with their own key, so only official release APKs report real usage.
        if (!BuildConfig.DEBUG && !isOfficialRelease()) {
            Toast.makeText(
                this,
                L.app.picoDevApplication.unofficialBuildAnalyticsDisabled,
                Toast.LENGTH_LONG
            ).show()
            return
        }
        safeInit("TelemetryDeck") {
            TelemetryDeck.start(
                this,
                TelemetryDeck.Builder()
                    .appID("5BE26330-812A-4293-AFB7-F5C98EAE4B82")
                    .namespace("ai.picodev")
                    .defaultUser(SimpleTelemetryId.getUniqueUserId(this))
                    .testMode(BuildConfig.DEBUG)
                    .showDebugLogs(BuildConfig.DEBUG)
                    // Only installs and updates are reported (the free plan counts every signal): nothing per app open,
                    // and no session tracking (the SDK's would send a signal per open and time how long the app is used)
                    .sendNewSessionBeganSignal(false)
                    .sessionProvider(NoSessionTracking())
                    // the SDK's own file cache crashes the app when storage is full (see SafeSignalCache)
                    .signalCache(SafeSignalCache(cacheDir))
            )
        }
        safeInit("TelemetryInstallReport") { reportInstallOrUpdate() }
    }

    /** Logs a startup failure and keeps going; a broken optional subsystem must not blank the app. */
    private inline fun safeInit(name: String, block: () -> Unit) {
        try {
            block()
        } catch (t: Throwable) {
            Log.e(TAG, "Startup initializer $name failed", t)
        }
    }

    /**
     * One signal per install ("App.installed") and per update ("App.updated"); TelemetryDeck adds the app version,
     * Android version and device to it. The version last reported is remembered, so each one is sent only once.
     */
    private fun reportInstallOrUpdate() = runCatching {
        val info = packageManager.getPackageInfo(packageName, 0)
        val version = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) info.longVersionCode
        else @Suppress("DEPRECATION") info.versionCode.toLong()
        val prefs = getSharedPreferences("telemetry_device_prefs", MODE_PRIVATE)
        val reported = prefs.getLong("reported_version_code", -1L)
        if (reported == version) return@runCatching
        // nothing reported yet: a fresh install, or an update from a version that didn't report updates
        val installed = reported == -1L && info.firstInstallTime == info.lastUpdateTime
        val params = if (reported == -1L) emptyMap() else mapOf("previousVersionCode" to reported.toString())
        TelemetryDeck.signal(if (installed) "App.installed" else "App.updated", params, null, null)
        prefs.edit().putLong("reported_version_code", version).apply()
    }

    /** Session tracking that does nothing: keeps one session ID per app start, sends no signals. */
    private class NoSessionTracking : TelemetryDeckSessionManagerProvider {
        private var sessionId: UUID = UUID.randomUUID()
        override fun register(ctx: Context?, client: TelemetryDeckSignalProcessor) {}
        override fun stop() {}
        override fun getCurrentSessionID(): UUID = sessionId
        override fun startNewSession(sessionID: UUID) { sessionId = sessionID }
        override fun setFirstSessionID(sessionID: UUID) { sessionId = sessionID }
    }

    /**
     * Stands in for the SDK's PersistentSignalCache, which writes cache/telemetrydeck.json at start and on every send
     * tick without catching errors: with storage full (ENOSPC) its background timer threw and crashed the app.
     * Same file and format (signals queued by older versions are still sent), but a failed read or write is ignored
     * (the signals stay in memory), and the file is written only when the queue changes.
     */
    private class SafeSignalCache(dir: File) : SignalCache {
        private val file = File(dir, "telemetrydeck.json")
        private val serializer = ListSerializer(Signal.serializer())
        private val queue: MutableList<Signal> =
            runCatching { Json.decodeFromString(serializer, file.readText()) }.getOrDefault(emptyList()).toMutableList()

        @Synchronized override fun add(signal: Signal) {
            queue += signal
            save()
        }

        @Synchronized override fun empty(): List<Signal> {
            if (queue.isEmpty()) return emptyList()
            val all = queue.toList()
            queue.clear()
            save()
            return all
        }

        @Synchronized override fun count(): Int = queue.size

        private fun save() {
            runCatching { file.writeText(Json.encodeToString(serializer, queue)) }
        }
    }

    private fun isOfficialRelease(): Boolean = runCatching {
        val signatures: Array<Signature>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo ?: return false
            if (info.hasMultipleSigners()) info.apkContentsSigners else info.signingCertificateHistory
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures
        }
        signatures.orEmpty().any { sig ->
            MessageDigest.getInstance("SHA-256").digest(sig.toByteArray())
                .joinToString("") { "%02X".format(it) } == RELEASE_CERT_SHA256
        }
    }.getOrDefault(false)

    private companion object {
        const val TAG = "PicoDevStartup"
        // SHA-256 of the release.jks certificate (alias "Pico Dev"). Public, safe to commit.
        const val RELEASE_CERT_SHA256 = "CCF2C681063BA941641519E4EAC7F024E92880CCD06EED5070DAA9717380F315"
    }
}
