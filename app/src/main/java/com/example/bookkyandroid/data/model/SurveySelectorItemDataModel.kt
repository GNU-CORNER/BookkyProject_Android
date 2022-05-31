package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class SurveySelectorItemDataModel(
    @SerializedName("TMID")
    val TMID : Int,
    @SerializedName("nameTag")
    val nameTag : String
)