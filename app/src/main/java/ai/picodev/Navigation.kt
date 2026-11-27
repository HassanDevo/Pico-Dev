package ai.picodev

import androidx.compose.runtime.Composable
import ai.picodev.ui.chat.ChatScreen
import ai.picodev.ui.chat.ChatViewModel

@Composable
fun MainNavigation(viewModel: ChatViewModel? = null) {
    if (viewModel != null) {
        ChatScreen(viewModel = viewModel)
    } else {
        ChatScreen()
    }
}
