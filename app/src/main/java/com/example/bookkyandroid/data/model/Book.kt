package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class Book (
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val result: List<BookResult>,
    @SerializedName("errorMessage") val errorMessage: String
        )