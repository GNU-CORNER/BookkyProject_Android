package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserSingUpResult (
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("pushToken") val pushToken : String,
    @SerializedName("pushNoti") val pushNoti: String
)