package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CommunityQnAReplyDetailDataModel(

    @SerializedName("title")    val title : String?,
    @SerializedName("contents")    val contents : String?,
    @SerializedName("views")    val views : Int?,
    @SerializedName("createAt")    val createAt : String?,
    @SerializedName("updateAt")    val updateAt : String?,
    @SerializedName("like")    val like : ArrayList<Int>?,
    @SerializedName("parentQPID")    val parentQPID : Int?,
    @SerializedName("postImage")    val postImage : ArrayList<String>?,
    @SerializedName("TBID")    val TBID : Int?,
    @SerializedName("nickname")    val nickname : String?,
    @SerializedName("thumbnail")    val thumbnail : String?,
    @SerializedName("PID")    val PID : Int?,
    @SerializedName("commentCnt")    val commentCnt : Int?,
    @SerializedName("isLiked")    val isLiked : Boolean?,
    @SerializedName("isAccessible")    val isAccessible : Boolean?,
    @SerializedName("Book")    val Book : BookDetailDataModel?,


    )
