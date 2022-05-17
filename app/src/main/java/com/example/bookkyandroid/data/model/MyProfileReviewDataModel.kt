package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class MyProfileReviewDataModel(
    @SerializedName("RID")
    val RID : Int?,
    @SerializedName("BID")
    val BID : Int?,
    @SerializedName("UID")
    val UID : Int?,
    @SerializedName("contents")
    val contents : String?,
    @SerializedName("views")
    val views : Int?,
    @SerializedName("createAt")
    val createAt : String?,
    @SerializedName("rating")
    val rating : Float?,
    @SerializedName("likeCnt")
    val likeCnt : Int?,
    @SerializedName("isLiked")
    val isLiked : Boolean?,
    @SerializedName("isAccessible")
    val isAccessible : Boolean?,
    @SerializedName("nickname")
    val nickname : String?,
    @SerializedName("AUTHOR")
    val AUTHOR : String?,
    @SerializedName("thumbnail")
    val thumbnail : String?,
    @SerializedName("bookTitle")
    val bookTitle : String?
)