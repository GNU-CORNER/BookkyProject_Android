package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class WriteCommentBodyDataModel (
    @SerializedName("comment")
    val contents : String,
    @SerializedName("parentID")
    val parentQPID : Int,
    @SerializedName("PID")
    val PID : Int
        )