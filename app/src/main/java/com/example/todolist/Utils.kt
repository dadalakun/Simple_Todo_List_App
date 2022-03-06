package com.example.todolist

import java.text.SimpleDateFormat
import java.util.*

// Return the formatted date
fun convertDateToString(dt: Date): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dt)
}

// Used to call c function
class ObjectWrapper {
    companion object {
        init {
            System.loadLibrary("daily_quote")
        }
    }

    external fun QuoteToday(): String
}

