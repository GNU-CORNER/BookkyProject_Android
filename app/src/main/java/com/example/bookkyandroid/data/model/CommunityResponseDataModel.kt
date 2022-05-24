package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityResponseDataModel(
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("result")
    val result : ArrayList<CommunityPostDataModel>?,
    @SerializedName("errorMessage")
    val errorMessage : String?
)
