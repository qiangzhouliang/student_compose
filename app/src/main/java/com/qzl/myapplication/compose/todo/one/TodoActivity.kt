package com.qzl.myapplication.compose.todo.one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.qzl.myapplication.ui.theme.MyApplicationTheme
import com.qzl.myapplication.compose.todo.four.TodoViewModel

class TodoActivity : ComponentActivity() {
    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                TodoActivityScreen()
            }
        }
    }

    @Composable
    private fun TodoActivityScreen() {
        /*val items = listOf<TodoItem>(
            TodoItem("Learn compose", TodoIcon.Event),
            TodoItem("task the codelab"),
            TodoItem("apply state", TodoIcon.Done),
            TodoItem("Build dynamic UIs", TodoIcon.Square),
        )*/

        com.qzl.myapplication.compose.todo.four.TodoScreen(
            items = todoViewModel.todoItems,
            currentlyEditing = todoViewModel.currentEditItem,
            onAddItem = todoViewModel::addItem,
            onRemoveItem = todoViewModel::removeItem,
            onStartEdit = todoViewModel::onEditItemSelected,
            onEditItemChange = todoViewModel::onEditItemChange,
            onEditDone = todoViewModel::onEditDone
        )
    }
}
