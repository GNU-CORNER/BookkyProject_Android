package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class ModifyProfileResponseDataModel (
    @SerializedName("route")
    val route : String,
    @SerializedName("nickname")
    val nickname : String
    )