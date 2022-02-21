package com.example.todolist.addtodo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTodoViewModel(val database: TodoDatabaseDao,
                       application: Application) : AndroidViewModel(application) {

    var duedate = MutableLiveData<String>()
    var created_date = MutableLiveData<String>()

    init {
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt = c.time
        duedate.value = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dt)
        created_date.value = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
    }

    fun submit(title: String, detail: String) {
        viewModelScope.launch {
            val newTodo = Todo(title = title, detail = detail, duedate = duedate.value.toString())
            insert(newTodo)
            Log.i("mumi", "Add todo(\nt: ${title},\nd: ${detail},\ndue: ${duedate.value}, \ncur: ${created_date.value}\n)")
        }
    }

    private suspend fun insert(todo: Todo) {
        database.insert(todo)
    }
}