package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDatabaseDao {

    @Insert
    suspend fun insert(todo: Todo)

    @Query("SELECT * FROM todo_table ORDER BY todoId DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("DELETE FROM todo_table")
    suspend fun clear()

}