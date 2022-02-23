package com.example.todolist

import java.text.SimpleDateFormat
import java.util.*

fun convertDateToString(dt: Date): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dt)
}