package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class SurveySelectorItemDataModel(
    @SerializedName("TID")
    val TID : Int,
    @SerializedName("nameTag")
    val nameTag : String
)