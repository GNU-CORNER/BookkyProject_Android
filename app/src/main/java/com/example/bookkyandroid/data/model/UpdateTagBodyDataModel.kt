package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UpdateTagBodyDataModel(
    @SerializedName("tag")
    val tag : ArrayList<Int>
)