package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class BookDetailDataModel(
    @SerializedName("TBID") val TBID : Int,
    @SerializedName("TITLE") val TITLE : String,
    @SerializedName("SUBTITLE") val SUBTITLE : String,
    @SerializedName("AUTHOR") val AUTHOR : String,
    @SerializedName("ISBN") val ISBN : String,
    @SerializedName("PUBLISHER") val PUBLISHER : String,
    @SerializedName("PRICE") val PRICE : String,
    @SerializedName("PAGE") val PAGE : String,
    @SerializedName("BOOK_INDEX") val BOOK_INDEX : String,
    @SerializedName("BOOK_INTRODUCTION") val BOOK_INTRODUCTION : String,
    @SerializedName("PUBLISH_DATE") val PUBLISH_DATE : String,
    @SerializedName("Allah_BID") val Allah_BID : String,
    @SerializedName("thumbnailImage") val thumbnailImage : String,
    @SerializedName("rating") val rating : Float,
    @SerializedName("tagData") val tagData : ArrayList<TagDataResponseDataModel>

)