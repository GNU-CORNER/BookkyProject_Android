package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CommunityDetailChildCommentDataModel(

    @SerializedName("parentID")
    val parentID : Int?,
    @SerializedName("comment")
    val comment : String?,
    @SerializedName("updateAt")
    val updateAt : String?,
    @SerializedName("like")
    val like : ArrayList<Int>?,
    @SerializedName("nickname")
    val nickname : String?,
    @SerializedName("thumbnail")
    val thumbnail : String?,
    @SerializedName("isAccessible")
    val isAccessible : Boolean?,
    @SerializedName("CID")
    val CID : Int?,
    )
