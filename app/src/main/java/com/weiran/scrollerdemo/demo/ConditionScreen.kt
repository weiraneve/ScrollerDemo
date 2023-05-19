package com.weiran.scrollerdemo.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ConditionScreen(viewModel: ConditionViewModel = viewModel()) {
    val context = LocalDensity.current
    val alphabetHeightInPixels = remember { with(context) { alphabetItemSize.toPx() } }
    var alphabetRelativeDragYOffset: Float? by remember { mutableStateOf(null) }
    var alphabetDistanceFromTopOfScreen: Float by remember { mutableStateOf(0F) }
    BoxWithConstraints {
        TaskListWithScroller(
            taskItems = viewModel.taskItems,
            topics = viewModel.topics,
            viewModel = viewModel,
            onAlphabetListDrag = { relativeDragYOffset, containerDistance ->
                alphabetRelativeDragYOffset = relativeDragYOffset
                alphabetDistanceFromTopOfScreen = containerDistance
            }
        )

        val yOffset = alphabetRelativeDragYOffset
        if (yOffset != null) {
            ScrollingBubble(
                boxConstraintMaxWidth = this.maxWidth,
                bubbleOffsetYFloat = yOffset + alphabetDistanceFromTopOfScreen,
                currAlphabetScrolledOn = yOffset.getIndexOfNameBasedOnYPosition(
                    alphabetHeightInPixels = alphabetHeightInPixels,
                    topics = viewModel.topics
                ),
            )
        }

    }
}

@Composable
fun TaskListWithScroller(
    taskItems: List<TaskItem>,
    topics: List<Topic>,
    viewModel: ConditionViewModel,
    onAlphabetListDrag: (Float?, Float) -> Unit,
) {
    val alphabetHeightInPixels = with(LocalDensity.current) { alphabetItemSize.toPx() }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var offsetMessage by remember { mutableStateOf(1) }

    Row {
        Spacer(modifier = Modifier.width(1300.dp))
        Text(text = offsetMessage.toString(), fontSize = 100.sp)
    }

    var previousIndex by remember { mutableStateOf(0) }

    LaunchedEffect(lazyListState.firstVisibleItemScrollOffset) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { currentIndex ->
                if (lazyListState.firstVisibleItemScrollOffset >= 0) {
                    if (currentIndex > previousIndex) {
                        viewModel.selectedTopicIndex.value += 1
                        offsetMessage += 1
                    } else if (currentIndex < previousIndex) {
                        viewModel.selectedTopicIndex.value -= 1
                        offsetMessage -= 1
                    }
                    previousIndex = currentIndex
                }
            }
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TaskItemList(
            Modifier
                .fillMaxHeight()
                .weight(1F),
            taskItems,
            lazyListState
        )

        AlphabetScroller(
            topics = topics,
            viewModel = viewModel,
            onAlphabetListDrag = { relativeDragYOffset, containerDistanceFromTopOfScreen ->
                onAlphabetListDrag(relativeDragYOffset, containerDistanceFromTopOfScreen)
                coroutineScope.launch {
                    // null case can happen if we go through list
                    // and we don't have a name that starts with I
                    val topicId = relativeDragYOffset?.getIndexOfIdBasedOnYPosition(
                        alphabetHeightInPixels = alphabetHeightInPixels,
                        topics = topics
                    ) ?: 1

                    taskItems.filter {
                        it.topicId == topicId
                    }.minByOrNull {
                        it.id
                    }.let {
                        if (it != null) {
                            lazyListState.scrollToItem(it.topicId - 1)
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun AlphabetScroller(
    topics: List<Topic>,
    viewModel: ConditionViewModel,
    onAlphabetListDrag: (relativeDragYOffset: Float?, distanceFromTopOfScreen: Float) -> Unit,
) {
    val alphabetList = topics.map { it.name }
    var distanceFromTopOfScreen by remember { mutableStateOf(0F) }

    Column(
        modifier = Modifier
            .width(70.dp)
            .onGloballyPositioned {
                distanceFromTopOfScreen = it.positionInRoot().y
            }
            .pointerInput(alphabetList) {
                detectVerticalDragGestures(
                    onDragStart = {
                        onAlphabetListDrag(it.y, distanceFromTopOfScreen)
                    },
                    onDragEnd = {
                        onAlphabetListDrag(null, distanceFromTopOfScreen)
                    }
                ) { change, _ ->
                    onAlphabetListDrag(
                        change.position.y,
                        distanceFromTopOfScreen
                    )
                }
            },
        verticalArrangement = Arrangement.Center,
    ) {
        for (content in alphabetList) {
            Text(
                modifier = Modifier
                    .height(alphabetItemSize)
                    .background(if (content == viewModel.topics[viewModel.selectedTopicIndex.value].name) Color.LightGray else Color.Transparent),
                text = content,
            )
        }
    }
}

@Composable
fun TaskItemList(
    modifier: Modifier,
    taskItems: List<TaskItem>,
    lazyListState: LazyListState
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        val itemsById = taskItems.groupBy { it.topicId }.values.toList()
        itemsIndexed(items = itemsById) { _, items ->
            Text(text = items[0].topicId.toString())
            TaskItems(
                taskItems = items
            )
        }
    }
}

@Composable
fun TaskItems(
    taskItems: List<TaskItem>
) {
    Column {
        taskItems.chunked(4).forEach { group ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                group.forEach {
                    TaskItem(it)
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    taskItem: TaskItem
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .background(Color.LightGray)
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .size(150.dp, 80.dp),
            text = taskItem.content,
        )
    }
}

@Composable
fun ScrollingBubble(
    boxConstraintMaxWidth: Dp,
    bubbleOffsetYFloat: Float,
    currAlphabetScrolledOn: String,
) {
    val bubbleSize = 96.dp
    Surface(
        shape = CircleShape,
        modifier = Modifier
            .size(bubbleSize)
            .offset(
                x = (boxConstraintMaxWidth - (bubbleSize + alphabetItemSize)),
                y = with(LocalDensity.current) {
                    bubbleOffsetYFloat.toDp() - (bubbleSize / 2)
                },
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currAlphabetScrolledOn
            )
        }
    }
}

internal fun Float.getIndexOfNameBasedOnYPosition(
    alphabetHeightInPixels: Float,
    topics: List<Topic>
): String {
    val alphabetList = topics.map { it.name }

    var index = ((this) / alphabetHeightInPixels).toInt()
    index = when {
        index > topics.size - 1 -> topics.size - 1
        index < 0 -> 0
        else -> index
    }
    return alphabetList[index]
}

internal fun Float.getIndexOfIdBasedOnYPosition(
    alphabetHeightInPixels: Float,
    topics: List<Topic>
): Int {
    val alphabetList = topics.map { it.id }

    var index = ((this) / alphabetHeightInPixels).toInt()
    index = when {
        index > topics.size - 1 -> topics.size - 1
        index < 0 -> 0
        else -> index
    }
    return alphabetList[index]
}

val alphabetItemSize = 24.dp