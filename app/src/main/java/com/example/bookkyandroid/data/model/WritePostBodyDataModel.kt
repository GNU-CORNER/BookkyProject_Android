package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class WritePostBodyDataModel (
    @SerializedName("title")
    val title : String,
    @SerializedName("contents")
    val contents : String,
    @SerializedName("TBID")
    val TBID : Int,
    @SerializedName("parentQPID")
    val parentQPID : Int,
    @SerializedName("Images")
    val Images : ArrayList<String>
        )