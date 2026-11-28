package ai.picodev.ui.chat

import ai.picodev.i18n.L
import ai.picodev.theme.ClaudeTerracotta
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * The floating jump-to-top / jump-to-bottom button. It reads the scroll flags itself (shown, direction, at the top,
 * at the bottom), so while scrolling only the button redraws when they change, not the chat screen.
 */
@Composable
internal fun ChatJumpButton(
    showScrollButton: () -> Boolean,
    scrollDirection: () -> ScrollDirection,
    isAtTop: () -> Boolean,
    isAtBottom: () -> Boolean,
    rowCount: Int,
    modifier: Modifier,
    onJump: (toTop: Boolean) -> Unit
) {
    val showUpArrow = showScrollButton() && scrollDirection() == ScrollDirection.UP && !isAtTop()
    val showDownArrow = showScrollButton() && scrollDirection() == ScrollDirection.DOWN && !isAtBottom()
    val isVisible = (showUpArrow || showDownArrow) && rowCount > 2

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + scaleIn(initialScale = 0.8f),
        exit = fadeOut() + scaleOut(targetScale = 0.8f),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .clickable(role = Role.Button) { onJump(showUpArrow) },
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            shadowElevation = 6.dp,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = if (showUpArrow) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (showUpArrow) L.chat.screen.jumpToTop else L.chat.screen.jumpToBottom,
                    tint = ClaudeTerracotta,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
