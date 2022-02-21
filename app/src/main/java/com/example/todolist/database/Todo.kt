package com.example.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var todoId: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "detail")
    var detail: String = "",

    @ColumnInfo(name = "due_date")
    var duedate: String = ""
)