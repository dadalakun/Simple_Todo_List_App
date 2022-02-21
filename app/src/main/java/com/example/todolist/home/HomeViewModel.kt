package com.example.todolist.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao

class HomeViewModel(val database: TodoDatabaseDao,
                    application: Application) : AndroidViewModel(application) {
    val todos = database.getAllTodo()
    val todosString = Transformations.map(todos) { todos ->
        formatTodos(todos)
    }

    init {

    }

    private fun formatTodos(tds: List<Todo>): String {
        Log.i("mumi", "${tds}")
        return "Mumimumi shin don don"
    }
}