package com.example.testapp.model

import com.google.gson.annotations.SerializedName


data class TodoModelResponse(
    @SerializedName("data") val todoListData: List<TodoListData> = emptyList()
)




