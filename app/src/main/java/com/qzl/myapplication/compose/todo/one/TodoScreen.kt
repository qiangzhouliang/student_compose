package com.qzl.myapplication.compose.todo.one

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.compose.todo.TodoItem
import com.qzl.myapplication.util.generatorRandomTodoItem
import kotlin.random.Random

@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        // 多行
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ){
            items(items = items){
                TodoRow(
                    todoItem = it,
                    onItemClicked = { onRemoveItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }
        // 按钮
        Button(
            onClick = { onAddItem(generatorRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "add random item")
        }
    }
}


@Composable
fun TodoRow(
    todoItem: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .clickable { onItemClicked(todoItem) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        // 子元素均匀分布
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = todoItem.task)

        // 将随机数存放起来
        val iconAlpha: Float = remember(todoItem.id) { randomTint() }
        Icon(
            imageVector = todoItem.icon.imageVector,
            // 通过 隐式 传参的方式改变着色
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todoItem.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f,0.9f)
}


@Preview
@Composable
fun PreTodoScreen() {
    val todoViewModel =  TodoViewModel()
    val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())

    TodoScreen(
        items = items,
        onAddItem = {todoViewModel.addItem(it)},
        onRemoveItem = { todoViewModel.removeItem(it) }
    )
}