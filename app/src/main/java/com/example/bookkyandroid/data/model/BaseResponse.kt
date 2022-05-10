package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val result: T,
    @SerializedName("errorMessage") val errorMessage: String
)