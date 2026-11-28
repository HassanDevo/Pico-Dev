package ai.picodev.ui.chat

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

/**
 * Keeps the end of the chat on screen while the agent works ([working]): the scroll is requested in the same
 * composition as the new rows, so it applies in the same layout pass and the end never drops below the screen while a
 * reply grows. It reads the auto-scroll and dragging flags itself: when they flip (every scroll starts and ends with
 * them) only this recomposes, not the chat screen.
 */
@Composable
internal fun FollowTail(
    listState: LazyListState,
    // the rows themselves, not their count: a reply growing inside its last row makes a new list of the same size,
    // and the end must be pinned again for it
    rows: List<ChatFeedItem>,
    working: Boolean,
    shouldAutoScroll: () -> Boolean,
    isUserDragging: () -> Boolean
) {
    if (rows.isNotEmpty() && working && shouldAutoScroll() && !isUserDragging()) {
        SideEffect { listState.requestScrollToItem(rows.size) }
    }
}
