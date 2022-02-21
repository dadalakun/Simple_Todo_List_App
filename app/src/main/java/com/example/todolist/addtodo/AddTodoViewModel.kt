package com.example.todolist.addtodo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTodoViewModel(val database: TodoDatabaseDao,
                       application: Application) : AndroidViewModel(application) {
//    private val allTodo = database.getAllTodo()

    var duedate = MutableLiveData<String>()

    init {
        duedate.value = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
    }

    fun onFinish(title: String, detail: String) {
//        Log.i("mumi", "title: ${title}, detail: ${detail}, date: ${duedate.value}")
        viewModelScope.launch {
            val newTodo = Todo(title = title, detail = detail, duedate = duedate.value.toString())
            insert(newTodo)
        }
    }

    fun showAll() {
        viewModelScope.launch {
//            val exists = database.exists("title A")
//            Log.i("mumi", "${exists}")
//            val todo = database.get("title A")
//            Log.i("mumi", "${todo}")
        }
    }

    private suspend fun insert(todo: Todo) {
        database.insert(todo)
    }
}