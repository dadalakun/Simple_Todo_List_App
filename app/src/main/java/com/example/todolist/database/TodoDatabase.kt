package com.example.todolist.database

import android.content.Context
import androidx.room.*

// ref(drop db and create a new one): https://stackoverflow.com/questions/55226859/android-room-persistance-library-drop-table
@Database(entities = [Todo::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract val todoDatabaseDao: TodoDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}