package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeCommunityDataModel(
    @SerializedName("title")
    val title : String,
    @SerializedName("updateAt")
    val updateAt : String,
    @SerializedName("PID")
    val PID : Int,
    @SerializedName("communityType")
    val communityType : Int
    )