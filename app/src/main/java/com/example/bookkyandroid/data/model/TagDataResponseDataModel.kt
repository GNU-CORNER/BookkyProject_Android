package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class TagDataResponseDataModel (
    @SerializedName("tag")
    val tag : String,
    @SerializedName("TMID")
    val TMID : Int
        )