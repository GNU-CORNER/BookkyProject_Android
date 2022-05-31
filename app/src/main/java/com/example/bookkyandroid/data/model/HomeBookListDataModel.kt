package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeBookListDataModel(
    @SerializedName("tag")
    val tag : String,
    @SerializedName("TMID")
    val TMID : Int,
    @SerializedName("data")
    val data : ArrayList<HomeBookDataModel?>
    )
