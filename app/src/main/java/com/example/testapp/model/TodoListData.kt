package com.example.testapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TodoListData(
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("title") val title: String,
    @ColumnInfo(name = "date")
    @SerializedName("due_on") val date: String,
    @SerializedName("status") val status: String
)