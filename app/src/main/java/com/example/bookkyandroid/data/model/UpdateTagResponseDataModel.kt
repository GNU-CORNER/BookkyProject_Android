package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class UpdateTagResponseDataModel(
    @SerializedName("tag")
    val tag:ArrayList<String?>
)