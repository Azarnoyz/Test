package com.example.testapp.db

import androidx.room.*
import com.example.testapp.model.TodoListData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todolistdata")
    suspend fun getAll(): List<TodoListData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todoLists: List<TodoListData>?)

    @Delete
    suspend fun delete(todoList: TodoListData)

}