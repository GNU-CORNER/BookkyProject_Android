package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityDetailResponseDataModel(
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("result")
    val result : CommunityDetailResultResponseDataModel?,
    @SerializedName("errorMessage")
    val errorMessage : String?
)
