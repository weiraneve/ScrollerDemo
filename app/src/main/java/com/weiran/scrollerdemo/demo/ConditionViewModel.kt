package com.weiran.scrollerdemo.demo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ConditionViewModel : ViewModel() {
    val topics = listOf(
        Topic(id = 1, name = "Topic 1"),
        Topic(id = 2, name = "Topic 2"),
        Topic(id = 3, name = "Topic 3"),
        Topic(id = 4, name = "Topic 4"),
        Topic(id = 5, name = "Topic 5"),
        Topic(id = 6, name = "Topic 6"),
        Topic(id = 7, name = "Topic 7"),
        Topic(id = 8, name = "Topic 8"),
        Topic(id = 9, name = "Topic 9"),
        Topic(id = 10, name = "Topic 10"),
        Topic(id = 11, name = "Topic 11"),
        Topic(id = 12, name = "Topic 12"),
        Topic(id = 13, name = "Topic 13"),
        Topic(id = 14, name = "Topic 14"),
        Topic(id = 15, name = "Topic 15"),
        Topic(id = 16, name = "Topic 16"),
        Topic(id = 17, name = "Topic 17"),
        Topic(id = 18, name = "Topic 18"),
        Topic(id = 19, name = "Topic 19"),
        Topic(id = 20, name = "Topic 20"),
        Topic(id = 21, name = "Topic 21"),
        Topic(id = 22, name = "Topic 22"),
        Topic(id = 23, name = "Topic 23"),
        Topic(id = 24, name = "Topic 24"),
    )

    val taskItems = listOf(
        TaskItem(
            id = 1,
            topicId = 1,
            content = "Content 1.1"
        ),
        TaskItem(
            id = 2,
            topicId = 1,
            content = "Content 1.2"
        ),

        TaskItem(
            id = 3,
            topicId = 2,
            content = "Content 2.1"
        ),
        TaskItem(
            id = 4,
            topicId = 2,
            content = "Content 2.2"
        ),
        TaskItem(
            id = 5,
            topicId = 2,
            content = "Content 2.3"
        ),
        TaskItem(
            id = 6,
            topicId = 2,
            content = "Content 2.4"
        ),
        TaskItem(
            id = 7,
            topicId = 2,
            content = "Content 2.5"
        ),
        TaskItem(
            id = 8,
            topicId = 2,
            content = "Content 2.6"
        ),
        TaskItem(
            id = 9,
            topicId = 2,
            content = "Content 2.7"
        ),
        TaskItem(
            id = 10,
            topicId = 2,
            content = "Content 2.8"
        ),
        TaskItem(
            id = 11,
            topicId = 2,
            content = "Content 2.9"
        ),

        TaskItem(
            id = 12,
            topicId = 3,
            content = "Content 3.1"
        ),
        TaskItem(
            id = 13,
            topicId = 3,
            content = "Content 3.2"
        ),
        TaskItem(
            id = 14,
            topicId = 3,
            content = "Content 3.3"
        ),
        TaskItem(
            id = 15,
            topicId = 3,
            content = "Content 3.4"
        ),
        TaskItem(
            id = 16,
            topicId = 3,
            content = "Content 3.5"
        ),
        TaskItem(
            id = 17,
            topicId = 3,
            content = "Content 3.6"
        ),
        TaskItem(
            id = 18,
            topicId = 3,
            content = "Content 3.7"
        ),
        TaskItem(
            id = 19,
            topicId = 3,
            content = "Content 3.8"
        ),
        TaskItem(
            id = 20,
            topicId = 3,
            content = "Content 3.9"
        ),

        TaskItem(
            id = 21,
            topicId = 4,
            content = "Content 4.1"
        ),
        TaskItem(
            id = 22,
            topicId = 4,
            content = "Content 4.2"
        ),
        TaskItem(
            id = 23,
            topicId = 4,
            content = "Content 4.3"
        ),
        TaskItem(
            id = 24,
            topicId = 4,
            content = "Content 4.4"
        ),
        TaskItem(
            id = 25,
            topicId = 4,
            content = "Content 4.5"
        ),
        TaskItem(
            id = 26,
            topicId = 4,
            content = "Content 4.6"
        ),
        TaskItem(
            id = 27,
            topicId = 4,
            content = "Content 4.7"
        ),
        TaskItem(
            id = 28,
            topicId = 4,
            content = "Content 4.8"
        ),
        TaskItem(
            id = 29,
            topicId = 4,
            content = "Content 4.9"
        ),

        TaskItem(
            id = 30,
            topicId = 5,
            content = "Content 5.1"
        ),
        TaskItem(
            id = 31,
            topicId = 5,
            content = "Content 5.2"
        ),
        TaskItem(
            id = 32,
            topicId = 5,
            content = "Content 5.3"
        ),
        TaskItem(
            id = 33,
            topicId = 5,
            content = "Content 5.4"
        ),
        TaskItem(
            id = 34,
            topicId = 5,
            content = "Content 5.5"
        ),
        TaskItem(
            id = 35,
            topicId = 5,
            content = "Content 5.6"
        ),
        TaskItem(
            id = 36,
            topicId = 5,
            content = "Content 5.7"
        ),
        TaskItem(
            id = 37,
            topicId = 5,
            content = "Content 5.8"
        ),
        TaskItem(
            id = 38,
            topicId = 5,
            content = "Content 5.9"
        ),

        TaskItem(
            id = 39,
            topicId = 6,
            content = "Content 6.1"
        ),
        TaskItem(
            id = 40,
            topicId = 7,
            content = "Content 7.1"
        ),
        TaskItem(
            id = 41,
            topicId = 8,
            content = "Content 8.1"
        ),
        TaskItem(
            id = 42,
            topicId = 9,
            content = "Content 9.1"
        ),
        TaskItem(
            id = 43,
            topicId = 10,
            content = "Content 10.1"
        ),
        TaskItem(
            id = 44,
            topicId = 11,
            content = "Content 11.1"
        ),
        TaskItem(
            id = 45,
            topicId = 12,
            content = "Content 12.1"
        ),
        TaskItem(
            id = 46,
            topicId = 13,
            content = "Content 13.1"
        ),
        TaskItem(
            id = 47,
            topicId = 14,
            content = "Content 14.1"
        ),
        TaskItem(
            id = 48,
            topicId = 15,
            content = "Content 15.1"
        ),
        TaskItem(
            id = 49,
            topicId = 16,
            content = "Content 16.1"
        ),
        TaskItem(
            id = 50,
            topicId = 17,
            content = "Content 17.1"
        ),
        TaskItem(
            id = 51,
            topicId = 18,
            content = "Content 18.1"
        ),
        TaskItem(
            id = 52,
            topicId = 19,
            content = "Content 19.1"
        ),
        TaskItem(
            id = 53,
            topicId = 20,
            content = "Content 20.1"
        ),
        TaskItem(
            id = 54,
            topicId = 21,
            content = "Content 21.1"
        ),
        TaskItem(
            id = 55,
            topicId = 22,
            content = "Content 22.1"
        ),
        TaskItem(
            id = 56,
            topicId = 23,
            content = "Content 23.1"
        ),
        TaskItem(
            id = 57,
            topicId = 24,
            content = "Content 24.1"
        ),
    )

    val selectedTopicIndex = mutableStateOf(0)
}
