package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class MyProfilePostDataModel(
    @SerializedName("PID")
    val PID : Int?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("contents")
    val contents : String?,
    @SerializedName("communityType")
    val communityType : Int?,
    @SerializedName("commentCnt")
    val commentCnt : Int?,
    @SerializedName("likeCnt")
    val likeCnt : Int?
)