package com.qzl.myapplication.compose.todo.one

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qzl.myapplication.compose.todo.TodoItem

class TodoViewModel: ViewModel() {
    private var _todoItems = MutableLiveData(listOf<TodoItem>())
    var todoItems: LiveData<List<TodoItem>> =  _todoItems

    fun addItem(todoItem: TodoItem){
        _todoItems.value = _todoItems.value!! + listOf(todoItem)
    }

    fun removeItem(todoItem: TodoItem){
        _todoItems.value = _todoItems.value!!.toMutableList().also { it.remove(todoItem) }
    }

}