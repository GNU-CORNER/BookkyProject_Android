package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunitySubDataModel(
    @SerializedName("commentCnt")
    val commentCnt : Int?,
    @SerializedName("likeCnt")
    val likeCnt : Int?
)
