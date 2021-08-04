package com.example.testapp.network

import com.example.testapp.model.TodoModelResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getTodosList(): Response<TodoModelResponse>
}