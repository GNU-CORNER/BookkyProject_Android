package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class InitPasswordBodyDataModel(
    @SerializedName("email")
    val email : String,
    @SerializedName("pwToken")
    val pwToken : String
)