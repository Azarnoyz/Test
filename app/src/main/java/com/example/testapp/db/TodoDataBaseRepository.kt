package com.example.testapp.db

import com.example.testapp.model.TodoListData
import javax.inject.Inject

class TodoDataBaseRepository @Inject constructor(
    private val dataBase: AppDataBase
) {

    suspend fun getAll(): List<TodoListData> = dataBase.todoDao().getAll()

    suspend fun insertAll(users: List<TodoListData>?) = dataBase.todoDao().insertAll(users)
}