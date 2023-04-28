package com.qzl.myapplication.util

import com.qzl.myapplication.compose.todo.TodoIcon
import com.qzl.myapplication.compose.todo.TodoItem

/**
 * 创建随机数据
 * @return TodoItem
 */
fun generatorRandomTodoItem(): TodoItem {
    val message = listOf(
        "learn Compos",
        "learn state",
        "build dynamic UIs",
        "learn Unidirectional Data Flow",
    ).random()

    val icon = TodoIcon.values().random()
    return TodoItem(message, icon)
}