package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

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

}