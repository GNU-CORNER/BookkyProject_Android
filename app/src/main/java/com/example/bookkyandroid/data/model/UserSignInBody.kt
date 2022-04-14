package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserSignInBody (
    @SerializedName("email")
    val email: String,
    @SerializedName("pwToken")
    val pwToken: String,
    @SerializedName("loginMethod")
    val loginMethod : Int = 0
)