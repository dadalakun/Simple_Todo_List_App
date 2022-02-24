package com.example.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var todoId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "created_date")
    var created_date: Date = Date(),

    @ColumnInfo(name = "due_date")
    var due_date: Date = Date(),

    @ColumnInfo(name = "location")
    var location: String = "None"

)

@Entity(tableName = "today_quote_table")
data class TodayQuote(
    @PrimaryKey
    var quoteId: Long = 0L,

    @ColumnInfo(name = "today")
    var today: Date = Date(),

    @ColumnInfo(name = "quote")
    var quote: String = ""
)