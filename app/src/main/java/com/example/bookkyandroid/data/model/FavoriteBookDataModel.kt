package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteBookDataModel(
    @SerializedName("TBID")
    val TBID : Int,
    @SerializedName("TITLE")
    val TITLE : String,
    @SerializedName("thumbnailImage")
    val thumbnailImage : String
)