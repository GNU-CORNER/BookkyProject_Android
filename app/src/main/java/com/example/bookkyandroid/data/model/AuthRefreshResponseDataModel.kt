package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class AuthRefreshResponseDataModel (
    @SerializedName("access_token")
    val access_token :String
        )