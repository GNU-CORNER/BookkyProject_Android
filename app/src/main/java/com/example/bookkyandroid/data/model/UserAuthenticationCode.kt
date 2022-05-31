package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserAuthenticationCode(
    @SerializedName("email")
    val email : String,
    @SerializedName("code")
    val code : String
)