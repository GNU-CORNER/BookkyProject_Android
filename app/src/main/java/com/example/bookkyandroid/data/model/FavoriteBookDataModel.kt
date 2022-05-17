package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteBookDataModel(
    @SerializedName("BID")
    val BID : Int,
    @SerializedName("TITLE")
    val TITLE : String,
    @SerializedName("thumbnailImage")
    val thumbnailImage : String
)