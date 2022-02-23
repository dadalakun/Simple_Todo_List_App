package com.example.todolist.home

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.todolist.convertDateToString
import com.example.todolist.database.Todo
import java.util.*

@BindingAdapter("todoTitle")
fun TextView.setTodoTitle(item: Todo?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("todoDescription")
fun TextView.setTodoDescription(item: Todo?) {
    item?.let {
        text = item.description
    }
}

@BindingAdapter("todoDueDate")
fun TextView.setTodoDueDate(item: Todo?) {
    item?.let {
        text = convertDateToString(item.due_date)
    }
}

@BindingAdapter("todoDueDateDetail")
fun TextView.setTodoDueDateDetail(item: Todo?) {
    item?.let {
        text = "Due: ${convertDateToString(item.due_date)}"
    }
}

@BindingAdapter("todoCreatedDate")
fun TextView.setTodoCreatedDate(item: Todo?) {
    item?.let {
        text = "Created date: ${convertDateToString(item.created_date)}"
    }
}

@BindingAdapter("todoLocation")
fun TextView.setTodoLocation(item: Todo?) {
    item?.let {
        text = "Location: ${item.location}"
    }
}




