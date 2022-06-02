package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityResultResponseDataModel(
    @SerializedName("postList")
    val postList : ArrayList<CommunityPostDataModel>?,
    @SerializedName("total_size")
    val total_size : Int?
)
