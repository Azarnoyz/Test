package com.example.testapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.model.TodoListData

@Database(entities = [TodoListData::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

}