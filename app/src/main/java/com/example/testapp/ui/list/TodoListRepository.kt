package com.example.testapp.ui.list

import com.example.testapp.network.ApiService
import javax.inject.Inject

class TodoListRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTodosList() = apiService.getTodosList()

}