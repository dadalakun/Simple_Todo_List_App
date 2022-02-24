package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDatabaseDao {

    @Insert
    suspend fun insert(todo: Todo)

    @Query("SELECT * FROM todo_table WHERE todoId = :key")
    fun getTodoWithId(key: Long): LiveData<Todo>

    @Query("SELECT * FROM todo_table ORDER BY due_date ASC")
    fun getAllTodos(): LiveData<List<Todo>>

    @Query("DELETE FROM todo_table WHERE todoId = :key")
    suspend fun delete(key: Long)

    @Query("DELETE FROM todo_table")
    suspend fun clear()

    @Query("SELECT EXISTS (SELECT 1 FROM todo_table WHERE title = :title)")
    suspend fun exists(title: String): Boolean

    @Insert
    suspend fun insert_today_quote(today_quote: TodayQuote)

    @Update
    suspend fun update_today_quote(today_quote: TodayQuote)

    @Query("SELECT * FROM today_quote_table WHERE quoteId = :key")
    suspend fun get_today_quote(key: Long): TodayQuote

    @Query("DELETE FROM today_quote_table")
    suspend fun clear_today_quote_table()
}