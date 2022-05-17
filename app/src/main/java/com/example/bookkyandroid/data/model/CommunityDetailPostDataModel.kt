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
    @SerializedName("like")
    val like : ArrayList<Int>?,
    @SerializedName("UID")
    val UID : Int?,

    )
