package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CommunityDetailCommentDataModel(
    @SerializedName("ACID")
    val ACID : Int?,
    @SerializedName("UID")
    val UID : Int?,
    @SerializedName("APID")
    val APID : Int?,
    @SerializedName("parentID")
    val parentID : Int?,
    @SerializedName("comment")
    val comment : String?,
    @SerializedName("updateAt")
    val updateAt : String?,
    @SerializedName("like")
    val like : ArrayList<Int>?,

    )
