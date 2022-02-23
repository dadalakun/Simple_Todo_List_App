package com.example.todolist.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.todolist.convertDateToString
import com.example.todolist.database.Todo

@BindingAdapter("todoTitle")
fun TextView.setTodoTitle(item: Todo?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("todoDuedate")
fun TextView.setTodoDueDate(item: Todo?) {
    item?.let {
        text = convertDateToString(item.duedate)
    }
}

@BindingAdapter("todoDescription")
fun TextView.setTodoDescription(item: Todo?) {
    item?.let {
        text = item.description
    }
}