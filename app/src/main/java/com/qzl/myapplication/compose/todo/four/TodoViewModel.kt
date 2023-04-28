package com.qzl.myapplication.compose.todo.four

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qzl.myapplication.compose.todo.TodoItem

/**
 * 状态容器
 * @property todoItems SnapshotStateList<TodoItem>
 */
class TodoViewModel: ViewModel() {
    // todoItem 集合只读
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    // 当前正在编辑的 todoItem的索引位置
    private var currentEditPosition by mutableStateOf(-1)

    // 当前正在编辑的 todoItem 对象
    val currentEditItem: TodoItem?
        get() = if (currentEditPosition == -1) null else todoItems[currentEditPosition]


    fun addItem(todoItem: TodoItem){
        todoItems.add(todoItem)
    }

    fun removeItem(todoItem: TodoItem){
        todoItems.remove(todoItem)
        onEditDone()
    }

    fun onEditDone(){
        currentEditPosition = -1
    }

    // 当todoItem列表中的条目被选中时，传染该对象，获取它在列表中的索引位置
    fun onEditItemSelected(item: TodoItem){
        currentEditPosition = todoItems.indexOf(item)
    }

    // todoItem 编辑完成，重新给集合中的todoItem赋值
    // id属性不能修改，进行校验
    fun onEditItemChange(item: TodoItem){
        todoItems[currentEditPosition] = item
    }
}