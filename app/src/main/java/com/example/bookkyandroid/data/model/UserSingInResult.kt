package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserSingInResult (
    @SerializedName("email") val email: String,
    @SerializedName("pwToken") val pwToken: String,
    @SerializedName("nickname") val nickname : String
)