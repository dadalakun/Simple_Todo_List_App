package com.example.todolist.addtodo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class AddTodoViewModel : ViewModel() {
    var duedate = MutableLiveData<String>()

    init {
        duedate.value = Calendar.getInstance().time.toString()
    }

    fun onFinish(title: String, detail: String) {
        Log.i("mumi", "title: ${title}, detail: ${detail}, date: ${duedate.value}")
    }

}