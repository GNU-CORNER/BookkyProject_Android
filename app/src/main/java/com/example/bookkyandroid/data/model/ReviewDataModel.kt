package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class ReviewDataModel(
    @SerializedName("RID")
    val RID : Int,
    @SerializedName("TBID")
    val TBID : Int,
    @SerializedName("UID")
    val UID : Int,
    @SerializedName("contents")
    val contents : String,
    @SerializedName("views")
    val views : Int,
    @SerializedName("createAt")
    val createAt : String,
    @SerializedName("rating")
    val rating : Float,
    @SerializedName("isAccessible")
    val isAccessible : Boolean,
    @SerializedName("likeCnt")
    val likeCnt : Int,
    @SerializedName("isLiked")
    val isLiked : Boolean,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("AUTHOR")
    val AUTHOR : String,
    @SerializedName("bookTitle")
    val bookTitle : String,
    @SerializedName("thumbnail")
    val thumbnail : String
)