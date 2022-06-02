package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityPostDataModel(
    @SerializedName("PID")
    val PID : Int?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("contents")
    val contents : String?,
    @SerializedName("likeCnt")
    val likeCnt : Int?,
    @SerializedName("commentCnt")
    val commentCnt : Int?,
)
