package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class WriteBookSearchDataModel (
    @SerializedName("TBID")
    val TBID : Int,
    @SerializedName("TITLE")
    val TITLE : String,
    @SerializedName("AUTHOR")
    val AUTHOR : String,
    @SerializedName("thumbnailImage")
    val thumbnailImage : String,
    @SerializedName("PUBLISHER")
    val PUBLISHER : String,
    @SerializedName("tagData")
    val tagData : ArrayList<TagDataResponseDataModel>
    )