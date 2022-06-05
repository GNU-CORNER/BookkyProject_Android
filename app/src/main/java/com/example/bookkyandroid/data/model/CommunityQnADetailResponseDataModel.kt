package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityQnADetailResponseDataModel(
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("result")
    val result : CommunityQnADetailResultResponseDataModel?,
    @SerializedName("errorMessage")
    val errorMessage : String?
)
