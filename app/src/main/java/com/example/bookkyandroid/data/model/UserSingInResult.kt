package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserSingInResult (
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("pushToken") val pushToken : String,
    @SerializedName("pushNoti") val pushNoti: Boolean,
    @SerializedName("access_token") val access_token: String,
    @SerializedName("refresh_token") val refresh_token: String,
    @SerializedName("loginMethod") val loginMethod : Int
)