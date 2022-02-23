package com.example.todolist.addtodo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.convertDateToString
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTodoViewModel(val database: TodoDatabaseDao,
                       application: Application) : AndroidViewModel(application) {

    var due_date = MutableLiveData<Date>()
    var created_date = MutableLiveData<Date>()

    init {
        created_date.value = Date()
        // Set the default value of due_date to tomorrow
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt = c.time
        due_date.value = dt
    }

    fun submit(title: String, detail: String) {
        viewModelScope.launch {
            val newTodo = Todo(title = title, description = detail, duedate = due_date.value!!)
            insert(newTodo)
            Log.i("Add todo", "Add todo(\nt: ${title},\nd: ${detail},\ndue: ${due_date.value}, \ncur: ${created_date.value}\n)")
        }
    }

    private suspend fun insert(todo: Todo) {
        database.insert(todo)
    }
}