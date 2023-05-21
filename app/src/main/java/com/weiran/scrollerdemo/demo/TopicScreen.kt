package com.weiran.scrollerdemo.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.update

const val THRESHOLD_LAST_TOPIC = 6
const val HALF_TASK_HEIGHT = 40


@Composable
fun TopicScreen(viewModel: DemoViewModel = viewModel()) {
    val topicList = remember {
        viewModel.topicsList
    }
    val selectedTopic by viewModel.selectedTopicWithContent.collectAsStateWithLifecycle()
    TopicScreen(topicList, selectedTopic) { topic ->
        viewModel.selectedTopicWithContent.update { topic }
    }
}

@Preview(widthDp = 800, showBackground = true)
@Composable
fun TopicScreenPreview() {
    TopicScreen(provideTopic(), provideTopic().first()){}
}

@Composable
private fun TopicScreen(
    topicList: List<TopicWithContent>,
    selectedTopic: TopicWithContent,
    setSelectedTopic: (TopicWithContent) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Row {
            TasksList(Modifier.weight(1f), topicList, selectedTopic, setSelectedTopic)
            Spacer(modifier = Modifier.width(50.dp))
            TopicList(topicList, selectedTopic)
        }
    }
}

@Composable
fun TopicList(topicList: List<TopicWithContent>, selectedTopic: TopicWithContent) {
    val lazyTopicListState = rememberLazyListState()

    LaunchedEffect(key1 = selectedTopic) {
        lazyTopicListState.scrollToItem(topicList.indexOf(selectedTopic))
    }

    LazyColumn(state = lazyTopicListState) {
        items(topicList) { topicWithContent ->
            Text(text = topicWithContent.topic, Modifier.background(Color.LightGray).takeIf {
                selectedTopic.topic == topicWithContent.topic
            } ?: Modifier)
        }
    }
}

@Composable
fun TasksList(
    modifier: Modifier,
    topicList: List<TopicWithContent>,
    selectedTopic: TopicWithContent,
    setSelectedTopic: (TopicWithContent) -> Unit
) {
    val lazyListState = rememberLazyListState()

    // TODO: Just to simple realize the feature, PLEASE DO REFACTOR!
    LaunchedEffect(key1 = lazyListState.firstVisibleItemScrollOffset) {
        /* when the last visible topic up to our THRESHOLD_LAST_TOPIC, we need to handle the select index.
        * From the top of our visible topic, we have double of the THRESHOLD_LAST_TOPIC topics need to handle.
        * For the other case, default select the first topic.
        */
        if (lazyListState.layoutInfo.visibleItemsInfo.last().index >= topicList.size - THRESHOLD_LAST_TOPIC){
            /*
            * every scrolling of half topic, need add one index from the first item to select, thus it has THRESHOLD_LAST_TOPIC * 2 need to handle
            * */
            if (lazyListState.firstVisibleItemScrollOffset > HALF_TASK_HEIGHT){  // use half 1st topic height will be better, can calculate from TopicWithContent
                setSelectedTopic(topicList[lazyListState.firstVisibleItemIndex + (THRESHOLD_LAST_TOPIC * 2 - (topicList.lastIndex - lazyListState.firstVisibleItemIndex)) + 1])
            } else {
                setSelectedTopic(topicList[lazyListState.firstVisibleItemIndex + ( THRESHOLD_LAST_TOPIC * 2 - (topicList.lastIndex - lazyListState.firstVisibleItemIndex))])
            }
        }
        else {
            setSelectedTopic(topicList[lazyListState.firstVisibleItemIndex])
        }
    }

    LazyColumn(
        modifier,
        state = lazyListState
    ) {
        items(topicList) {
            Column(modifier = Modifier.border(1.dp, color = Color.Blue)) {
                Text(text = it.topic)
                TaskContent(it.content)
            }
        }
    }
}

@Composable
fun TaskContent(content: List<TaskItem>) {
    content.chunked(4).forEach { group ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            group.forEach {
                TaskItem(it)
            }
        }
    }
}
