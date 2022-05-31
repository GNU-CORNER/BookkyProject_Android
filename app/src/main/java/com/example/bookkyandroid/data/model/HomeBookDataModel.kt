package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeBookDataModel (
    @SerializedName("TBID")
    val TBID : Int?,
    @SerializedName("TITLE")
    val TITLE : String?,
    @SerializedName("AUTHOR")
    val AUTHOR : String?,
    @SerializedName("thumbnailImage")
    val thumbnailImage : String?
        )