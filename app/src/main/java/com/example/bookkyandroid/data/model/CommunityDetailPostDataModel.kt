package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CommunityDetailPostDataModel(
    @SerializedName("title")
    val title : String?,
    @SerializedName("contents")
    val contents : String?,
    @SerializedName("views")
    val views : Int?,
    @SerializedName("createAt")
    val createAt : String?,
    @SerializedName("updateAt")
    val updateAt : String?,
    @SerializedName("postImage")
    val postImage : ArrayList<String>?,
    @SerializedName("like")
    val like : ArrayList<Int>?,
    @SerializedName("nickname")
    val nickname : String?,
    @SerializedName("thumbnail")
    val thumbnail : String?,
    @SerializedName("isAccessible")
    val isAccessible : Boolean?,
    @SerializedName("isLiked")
    val isLiked : Boolean?,
    @SerializedName("TBID")
    val TBID : Int?,

    )
