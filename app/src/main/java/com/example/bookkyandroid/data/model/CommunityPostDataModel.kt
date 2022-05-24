package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityPostDataModel(
    @SerializedName("title")
    val title : String?,
    @SerializedName("contents")
    val contents : String?,
    @SerializedName("commentCnt")
    val commentCnt : Int?,
    @SerializedName("likeCnt")
    val likeCnt : Int?,
    @SerializedName("PID")
    val PID : Int?
)

