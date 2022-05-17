package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityPostDataModel(
    @SerializedName("APID")
    val APID : Int?,
    @SerializedName("title")
    val title : String?,
    @SerializedName("contents")
    val contents : String?,
)
