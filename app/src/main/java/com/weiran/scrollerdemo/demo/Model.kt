package com.weiran.scrollerdemo.demo

data class Topic(val id: Int, val name: String)

data class TaskItem(val id: Int, val topicId: Int, val content: String)
