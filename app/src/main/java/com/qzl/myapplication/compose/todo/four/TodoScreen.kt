package com.qzl.myapplication.compose.todo.four

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qzl.myapplication.compose.todo.TodoItem
import com.qzl.myapplication.util.generatorRandomTodoItem
import kotlin.random.Random
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

@Composable
fun TodoScreen(
    items: List<TodoItem>,
    currentlyEditing: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onStartEdit: KFunction1<TodoItem, Unit>,
    onEditItemChange: KFunction1<TodoItem, Unit>,
    onEditDone: KFunction0<Unit>
) {
    Column {
        // 当currentlyEditing（当前编辑条目）为空时，显示添加输入框
        // 否则进入编辑状态时，最顶部会显示 "Editing item"文本
        val enableTopSection = (currentlyEditing == null)
        // 输入框 外加一个灰色背景
        TodoItemInputBackground(elevate = true) {
            if (enableTopSection) {
                TodoItemEntryInput(onAddItem)
            } else {
                Text(
                    text = "Editing item",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
        // 多行
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ){
            items(items = items){ todo ->
                if (currentlyEditing?.id == todo.id){
                    TodoItemInlineEditor(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = { onRemoveItem(todo) }
                    )
                } else {
                    TodoRow(
                        todoItem = todo,
                        onItemClicked = { onStartEdit(todo) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

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