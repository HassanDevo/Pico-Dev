# Pico Dev — feature inventory

Generated from the checked-in Kotlin source. The UI is Jetpack Compose: every
`@Composable` function is a screen, dialog, sheet, card, widget or reusable UI piece.

- UI composables: **317** across **29** packages
- Manifest components: **44**
- Kotlin/Java source files: **454**

## Agent backends

- Antigravity / AGY cloud (`data/agent/agy`)
- Claude Code (`data/agent/claude`)
- Codex (`data/agent/codex`)
- OpenCode (`data/agent/opencode`)

## Major subsystems

- `data/adb` — 6 Kotlin files
- `data/admin` — 1 Kotlin files
- `data/agent` — 29 Kotlin files
- `data/androiddev` — 1 Kotlin files
- `data/audio` — 2 Kotlin files
- `data/automation` — 2 Kotlin files
- `data/changelog` — 1 Kotlin files
- `data/checkpoint` — 1 Kotlin files
- `data/context` — 1 Kotlin files
- `data/daemon` — 3 Kotlin files
- `data/egm` — 1 Kotlin files
- `data/file` — 1 Kotlin files
- `data/files` — 13 Kotlin files
- `data/github` — 1 Kotlin files
- `data/homescreen` — 9 Kotlin files
- `data/ide` — 1 Kotlin files
- `data/jarvis` — 17 Kotlin files
- `data/local` — 14 Kotlin files
- `data/media` — 1 Kotlin files
- `data/memory` — 3 Kotlin files
- `data/miniapps` — 17 Kotlin files
- `data/notes` — 2 Kotlin files
- `data/pc` — 2 Kotlin files
- `data/preferences` — 3 Kotlin files
- `data/rea` — 2 Kotlin files
- `data/receiver` — 4 Kotlin files
- `data/remote` — 31 Kotlin files
- `data/render` — 2 Kotlin files
- `data/run` — 2 Kotlin files
- `data/service` — 1 Kotlin files
- `data/skills` — 1 Kotlin files
- `data/ssh` — 2 Kotlin files
- `data/updater` — 3 Kotlin files
- `data/voice` — 9 Kotlin files
- `data/web` — 1 Kotlin files
- `data/x11` — 1 Kotlin files

## UI composables by package

### `.` (1)

`MainNavigation`

### `theme` (3)

`GeminiTheme`, `isAppInDarkTheme`, `isSystemInDarkThemeRobust`

### `ui/agents` (23)

`AccountsButton`, `AccountsDialog`, `AgentActionPill`, `AgentApprovalPanel`, `AgentChoiceDialog`, `AgentMark`, `AgentQuestionPanel`, `AgentSelector`, `AgentSetupPrompt`, `AgentSheetHeader`, `AgentStatusDot`, `AgentStatusIndicator`, `AgentStatusMenu`, `AgentStatusRow`, `AgentsApplyingDialog`, `AgentsSettingsPage`, `AgyAccountCard`, `AgyAccountsCard`, `AgyCliCard`, `BridgeVersionDialog`, `BridgeVersionLine`, `ConfirmSignOutDialog`, `SheetSectionLabel`

### `ui/browser` (2)

`BrowserDiagnosticOverlay`, `BrowserScreen`

### `ui/browser/mcp` (3)

`BrowserAgentLockOverlay`, `BrowserAgentOverlay`, `BrowserJsDialogCard`

### `ui/bubble` (2)

`FloatingBubbleContent`, `FloatingChatWindow`

### `ui/chat` (4)

`ChatScreen`, `ChatToastBanner`, `SlashCommandOverlay`, `StepGroupPill`

### `ui/checkpoint` (3)

`CheckpointRestoreSheet`, `ProjectCheckpointsScreen`, `TrackProjectDialog`

### `ui/claude` (22)

`ClaudeCard`, `ClaudeContextDialog`, `ClaudeControlsStrip`, `ClaudeEffortSheet`, `ClaudeLoginDialog`, `ClaudeModelSheet`, `ClaudeNavCard`, `ClaudePermissionModeSheet`, `ClaudeProviderSwitchDialog`, `ClaudeSettingsGroup`, `ClaudeSettingsScreen`, `ClaudeStartupNotice`, `ClaudeStatusBanner`, `ClaudeStatusPrompt`, `ClaudeUsageDialog`, `ControlChip`, `ModelRow`, `ProvidersPage`, `ReadOnlySubagentNote`, `UsageBar`, `claudeCardColor`, `permissionModeColor`

### `ui/cloud` (6)

`AgyCloudSettingsCard`, `CloudChatStrip`, `CloudPreviewChooser`, `CloudPreviewDialog`, `CloudProjectsDialog`, `CloudRunCommandDialog`

### `ui/codex` (7)

`CodexControlsStrip`, `CodexMcpPage`, `CodexModelSheet`, `CodexPluginsPage`, `CodexSettingsGroup`, `CodexSettingsScreen`, `codexModeColor`

### `ui/components` (93)

`ActiveContextSummaryCard`, `AgentErrorCard`, `AgentPicker`, `AgentToolCallCard`, `AgentWorkingSpinner`, `AppIconImage`, `AppPermissionsDialog`, `AssistantMessageFooter`, `AttachmentSelectorBottomSheet`, `ChatAudioPlayer`, `ChatCommandRunDialog`, `ChatInputBar`, `ChatTelemetryDialog`, `ChatVideoPlayer`, `ChoiceQuestionnaireCard`, `ClaudeBadge`, `ClaudeWaveformIcon`, `CodeBlock`, `ConnectionStatusBadge`, `ContextSummarizeAlertBanner`, `ContextSummaryCheckpointBanner`, `ConversationLoadingSkeleton`, `CustomSystemPromptDialog`, `DrawerEngineWarmingUpView`, `EditSummaryDialog`, `EngineWarmingUpView`, `FileDetailsDialog`, `FileDetailsDialog`, `FloatingDiagnosticsOverlay`, `FloatingMascot`, `FloatingNetworkInspectorBubble`, `FloatingSwitcherWidget`, `FloatingTerminalInspector`, `FloatingVideoPlayerOverlay`, `FormattedInlineText`, `FullPromptDialog`, `FullScreenImageDialog`, `FullscreenVideoHost`, `GemmyOverlay`, `GemmyPreview`, `GitCloneDialog`, `InteractiveUiView`, `KeptWebView`, `LiveAudioWaveform`, `LiveSummarizingCard`, `LocalServerOutputDialog`, `LocalTerminalContent`, `LocalTerminalDialog`, `MarkdownAlertView`, `MarkdownBlockView`, `MarkdownContent`, `MarkdownDetailsView`, `MarkdownDocViewerModal`, `MarkdownImageView`, `MarkdownTableView`, `MarkdownVideoView`, `MemoryChipView`, `MermaidDiagramView`, `MermaidFullscreenDialog`, `MessageBubble`, `ModelTypingIndicator`, `NativeMathView`, `NetworkInspectorDialog`, `NewChatCheckingAuthPromptCard`, `NewChatOfflinePromptCard`, `NewChatSignInPromptCard`, `PageOverview`, `ProjectPickerDialog`, `ProvideBouncyOverscroll`, `QueuedMessagesCard`, `RawPayloadDialog`, `SelectTextSheet`, `ServerStoppedPromptCard`, `SharedChatLoadingDialog`, `SharedChatViewerModal`, `SidebarChatListSkeleton`, `SpeechToTextModeDialog`, `StemBlockView`, `SummaryModelPickerDialog`, `TerminalInspectorDialog`, `TerminalSessionsDrawer`, `ThinkingAccordion`, `TokenUsageDetailsSheet`, `TokenUsageTelemetryPill`, `UserMessageBubble`, `WaveDivider`, `WorkspaceFolderBrowserDialog`, `YouTubeVideoView`, `keptWebViewSlot`, `rememberBouncyLazyScroll`, `rememberBouncyOverscrollEffect`, `rememberTerminalDrawerState`, `shimmerBrush`

### `ui/drawer` (5)

`ArtifactsDrawerContent`, `ChatHistoryDrawer`, `HeaderIcon`, `ProfileDetailDialog`, `SearchChatsDialog`

### `ui/files` (7)

`FileExplorer`, `FileExplorerDialog`, `FileLinkOpenerHost`, `FilePickerSettingsCard`, `IncomingShareDialog`, `Tab`, `rememberFileLinkOpener`

### `ui/homescreen` (9)

`ChatAvatar`, `Disc`, `Glyph`, `GlyphDisc`, `HomeScreenSettingsCard`, `HomeWidgetTheme`, `Label`, `cardColor`, `widgetRoot`

### `ui/ide` (10)

`DaemonLogsDialog`, `FileTreeNodeItem`, `GitSourceControlView`, `IdeExecutionRunnerSheet`, `IdeScreen`, `ImageAssetViewer`, `NewProjectDialog`, `ProjectSidebar`, `UnifiedDiffViewer`, `getFileIconColor`

### `ui/jarvis` (15)

`IdleOrb`, `JarvisBotBadge`, `JarvisBotIcon`, `JarvisChatWelcome`, `JarvisCommandStrip`, `JarvisEventCard`, `JarvisOrb`, `JarvisOverlay`, `JarvisPanel`, `JarvisQuickChip`, `JarvisQuickSettings`, `JarvisSettingsScreen`, `JarvisSettingsSectionPage`, `QuickSwitchChip`, `SiriWaves`

### `ui/main` (4)

`Greeting`, `MainScreen`, `MainScreenPortraitPreview`, `MainScreenPreview`

### `ui/miniapps` (9)

`ActionSheet`, `AppIcon`, `AppInfoSheet`, `ConsoleSheet`, `DeveloperKeyDialog`, `MediaViewerDialog`, `NativeDialogHost`, `PackageInstaller`, `TypedConfirmDialog`

### `ui/models` (10)

`ActiveModelBadge`, `EditGroupsAction`, `EditGroupsBanner`, `GroupMoveButton`, `ModelSelectorBottomSheet`, `PillWindowRow`, `QuotaBadgeChip`, `QuotaGroupHeader`, `QuotaMeter`, `ThinkingSelectorBottomSheet`

### `ui/notes` (1)

`NotesScreen`

### `ui/onboarding` (3)

`GemmyIntroDialog`, `OnboardingStepScreen`, `TerminalLauncherOnboardingDialog`

### `ui/opencode` (7)

`OpenCodeControlsStrip`, `OpenCodeMcpPage`, `OpenCodeModelSheet`, `OpenCodeProvidersPage`, `OpenCodeSettingsGroup`, `OpenCodeSettingsScreen`, `openCodeModeColor`

### `ui/pc` (6)

`AddPcDialog`, `ConnectPcScreen`, `PcDrawerButton`, `PcQuickSheet`, `PcServersNotice`, `PcUnreachablePrompt`

### `ui/run` (2)

`RunBubbleHost`, `RunScreen`

### `ui/settings` (44)

`AdbConnectScreen`, `AgentHistoryScreen`, `AgentMemoryEntryRow`, `AgentMemoryScreen`, `AgentSkillsDialog`, `AgentSkillsPanel`, `AgyGitHubPluginDialog`, `AndroidAutomationScreen`, `AndroidDevScreen`, `CascadeMcpCatalogDialog`, `ChangelogScreen`, `CustomSlashCommandsScreen`, `EgmEntryRow`, `EgmScreen`, `FullScreenLocalToolsInstaller`, `GitHubActionRow`, `GitHubScreen`, `GlobalContextEntryRow`, `GlobalContextScreen`, `LanguageScreen`, `LanguageTextsScreen`, `LocalToolsInstallDialog`, `MarkdownViewerDialog`, `PluginIcon`, `PluginRepositoriesTab`, `ReaGroupsScreen`, `ReaScreen`, `ReadmePreviewDialog`, `SettingsDialog`, `SettingsRowGroup`, `SettingsRowList`, `SetupWithAiDialog`, `SetupWithAiLink`, `SkillDetailMarkdownDialog`, `SkillsAndPluginsSubScreen`, `StemRenderingScreen`, `SwitcherOverviewOptions`, `ThemeGallery`, `ThemeQuickPanel`, `VersionChange`, `WebViewExtensionMcpPicker`, `WebViewExtensionsScreen`, `X11DesktopScreen`, `rememberPageOverviewMode`

### `ui/settings/chroot` (7)

`ChrootBuildScreen`, `ChrootManageScreen`, `ChrootRuntimeCard`, `ChrootSetupDialog`, `ChrootSetupScreen`, `TermuxOnlyNotice`, `TermuxRuntimeCard`

### `ui/updater` (1)

`AppUpdateDialog`

### `ui/voice` (8)

`AgyVoiceCallPill`, `AgyVoiceCallScreen`, `AgyVoiceCallSettingsCard`, `AgyVoiceFloatingCircle`, `AgyVoiceFloatingPanel`, `AgyVoicePickerDialog`, `AgyVoiceTranscriptSheet`, `VoiceCallMarker`

## Manifest components

- activity-alias: .AntiTermAlias
- activity-alias: .ClassicTermAlias
- activity: .AntiTermAlias
- activity: .ClassicTermAlias
- activity: .MainActivity
- activity: .TerminalActivity
- activity: .crash.CrashActivity
- activity: .ui.bubble.FloatingChatActivity
- activity: ai.picodev.app.TermuxOpenActivity
- activity: ai.picodev.data.homescreen.HomeLaunchActivity
- activity: ai.picodev.data.jarvis.JarvisTalkActivity
- activity: ai.picodev.data.voice.VoiceCallLaunchActivity
- activity: ai.picodev.ui.homescreen.WidgetConfigActivity
- activity: ai.picodev.ui.miniapps.MiniAppActivity
- activity: ai.picodev.ui.miniapps.MiniAppInstallActivity
- activity: ai.picodev.ui.miniapps.MiniAppLaunchActivity
- activity: ai.picodev.ui.miniapps.MiniAppPageActivity
- activity: ai.picodev.ui.miniapps.MiniAppShareActivity
- activity: ai.picodev.ui.miniapps.MiniAppStoreActivity
- activity: ai.picodev.ui.miniapps.MiniAppsActivity
- activity: ai.picodev.ui.settings.ManageSpaceActivity
- provider: ai.picodev.data.files.PicoDevDocumentsProvider
- provider: ai.picodev.data.files.PicoDevFileProvider
- provider: com.telemetrydeck.sdk.TelemetryDeckInitProvider
- provider: rikka.shizuku.ShizukuProvider
- receiver: .data.receiver.TermuxApiReceiver
- receiver: ai.picodev.app.TermuxOpenReceiver
- receiver: ai.picodev.app.TermuxSystemReceiver
- receiver: ai.picodev.data.admin.PicoDevDeviceAdminReceiver
- receiver: ai.picodev.data.miniapps.MiniAppAlarmReceiver
- receiver: ai.picodev.data.miniapps.MiniAppBootReceiver
- receiver: ai.picodev.data.miniapps.MiniAppNotificationReceiver
- receiver: ai.picodev.ui.homescreen.ActionsWidgetReceiver
- receiver: ai.picodev.ui.homescreen.ChatWidgetReceiver
- receiver: ai.picodev.ui.homescreen.ChatsWidgetReceiver
- receiver: ai.picodev.ui.homescreen.HomeWidgetPinReceiver
- receiver: ai.picodev.ui.homescreen.ScriptsWidgetReceiver
- service: ai.picodev.app.TermuxService
- service: ai.picodev.data.adb.AdbPairingService
- service: ai.picodev.data.automation.AutomationAccessibilityService
- service: ai.picodev.data.jarvis.JarvisService
- service: ai.picodev.data.jarvis.JarvisTileService
- service: ai.picodev.data.voice.AgyVoiceCallService
- service: ai.picodev.data.voice.VoiceCallTileService
