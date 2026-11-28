package ai.picodev.ui.chat

import ai.picodev.domain.model.ChatMessage
import android.os.SystemClock
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

/** How often the chat screen takes message updates while the newest message is off screen (see [pacedMessages]). */
internal const val MESSAGE_PACE_MS = 250L

/**
 * The messages for the chat screen. While [slow] (no row of the newest message is on screen: the user reads older
 * messages while a reply may be streaming), updates are passed on at most every [MESSAGE_PACE_MS], always the newest
 * one: the screen and its list rebuild a few times a second instead of on every streamed word. Passed on at once:
 * every update when not [slow], the first list, another chat's list, a list that got shorter or emptied (edit,
 * rewind, reload), and the newest list the moment [slow] ends. Nothing is ever dropped: the last update always
 * arrives, at most [MESSAGE_PACE_MS] late.
 */
internal fun pacedMessages(
    source: StateFlow<List<ChatMessage>>,
    slow: () -> Boolean
): Flow<List<ChatMessage>> = channelFlow {
    var sent: List<ChatMessage>? = null
    var sentAt = 0L
    var timer: Job? = null
    suspend fun pass(list: List<ChatMessage>) {
        sent = list
        sentAt = SystemClock.uptimeMillis()
        send(list)
    }
    launch {
        // the newest message came on screen: what was held back follows at once
        snapshotFlow { slow() }.collect { isSlow ->
            if (!isSlow) {
                timer?.cancel()
                timer = null
                val newest = source.value
                if (newest !== sent) pass(newest)
            }
        }
    }
    source.collect { list ->
        val last = sent
        val urgent = last == null || !slow() || list.isEmpty() || last.isEmpty() || list.size < last.size ||
            list.first().conversationId != last.first().conversationId
        val wait = MESSAGE_PACE_MS - (SystemClock.uptimeMillis() - sentAt)
        if (urgent || wait <= 0) {
            timer?.cancel()
            timer = null
            pass(list)
        } else if (timer == null) {
            timer = launch {
                delay(wait)
                timer = null
                pass(source.value)
            }
        }
    }
}

/** The index of the first row of the newest message in [rows] (rows.size when no row belongs to a message). */
internal fun firstRowOfNewestMessage(rows: List<ChatFeedItem>): Int {
    var newest: String? = null
    var first = rows.size
    for (i in rows.indices.reversed()) {
        val id = rows[i].messageIdOrNull() ?: continue
        if (newest == null) newest = id else if (id != newest) break
        first = i
    }
    return first
}

/** The message a row belongs to (null for dividers and voice-call markers). */
internal fun ChatFeedItem.messageIdOrNull(): String? = when (this) {
    is ChatFeedItem.AssistantBlock -> messageId
    is ChatFeedItem.AssistantThinking -> messageId
    is ChatFeedItem.AssistantTyping -> messageId
    is ChatFeedItem.AssistantFooter -> message.id
    is ChatFeedItem.StreamingMessage -> message.id
    is ChatFeedItem.User -> message.id
    is ChatFeedItem.Summary -> message.id
    is ChatFeedItem.JarvisEvent -> message.id
    is ChatFeedItem.StepGroup -> steps.firstOrNull()?.messageId
    else -> null
}
