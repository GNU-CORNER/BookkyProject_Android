package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserEmailBody(
    @SerializedName("email")
    val email : String
)