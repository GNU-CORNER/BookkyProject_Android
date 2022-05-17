package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityResultResponseDataModel(
    @SerializedName("postList")
    val postList : ArrayList<CommunityPostDataModel>?,
    @SerializedName("userData")
    val userData : ArrayList<CommunityUserDataModel>?,
    @SerializedName("subData")
    val subData : ArrayList<CommunitySubDataModel>?
)
