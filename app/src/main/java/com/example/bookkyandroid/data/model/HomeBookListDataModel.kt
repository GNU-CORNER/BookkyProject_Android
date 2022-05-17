package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeBookListDataModel(
    @SerializedName("tag")
    val tag : String,
    @SerializedName("TID")
    val TID : Int,
    @SerializedName("data")
    val data : ArrayList<HomeBookDataModel?>
    )
