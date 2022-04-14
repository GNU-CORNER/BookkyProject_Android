package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeResponseDataModel(
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("result")
    val result : HomeResultResponseDataModel?,
    @SerializedName("errorMessage")
    val errorMessage : String?
    )
