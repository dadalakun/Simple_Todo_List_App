package com.example.todolist.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.todolist.database.TodoDatabaseDao
import com.example.todolist.ObjectWrapper
import com.example.todolist.database.TodayQuote
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(dataSource: TodoDatabaseDao,
                    application: Application) : ViewModel() {


    val database = dataSource
    val todos = database.getAllTodos()
    var quote = MutableLiveData<String>()

    /**
     * Navigation to the AddTodo fragment
     */
    private val _navigateToAddTodo = MutableLiveData<Boolean?>()
    val navigateToAddTodo: LiveData<Boolean?>
        get() = _navigateToAddTodo

    fun onAddTodoClicked() {
        _navigateToAddTodo.value = true
    }

    fun doneNavigation() {
        _navigateToAddTodo.value = null
    }

    /**
     * Navigation to the TodoDetail fragment
     */
    private val _navigateToTodoDetail = MutableLiveData<Long?>()
    val navigateToToDoDetail
        get() = _navigateToTodoDetail

    fun onTodoClicked(id: Long) {
        _navigateToTodoDetail.value = id
    }

    fun onTodoDetailNavigated() {
        _navigateToTodoDetail.value = null
    }


    private fun get_new_quote (old_quote: String): String {
        val obj = ObjectWrapper()
        var new_quote = obj.QuoteToday()
        // Loop until we get a different quote
        while(old_quote == new_quote) {
            new_quote = obj.QuoteToday()
        }
        return new_quote
    }

    init {
        // ref (Using C++ in Kotlin project)
        // 1. https://developer.android.com/studio/projects/add-native-code#create-sources
        // 2. https://stackoverflow.com/questions/51613950/kotlin-ndk-and-c-interactions
        viewModelScope.launch{
            val quote_tmp: String
            val q = database.get_today_quote(0)
            Log.i("Quote", "${q}")
            if(q == null) {
                // If the table is empty, get a new quote and store it into the db
                quote_tmp = get_new_quote("")
                database.insert_today_quote(TodayQuote(0, Date(), quote_tmp))
            } else {
                val fmt = SimpleDateFormat("yyyyMMdd")
                if (fmt.format(q.today) != fmt.format(Date())) {
                    // If the quote inside the db is expired, we get a new, different quote and
                    // store it into the db
                    Log.i("Quote", "NOT same date, update the quote")
                    quote_tmp = get_new_quote(q.quote)
                    database.update_today_quote(TodayQuote(0, Date(), quote_tmp))
                } else {
                    // The daily quote is up to date
                    Log.i("Quote", "same date")
                    quote_tmp = q.quote
                }
            }
            quote.value = quote_tmp
        }
    }

}