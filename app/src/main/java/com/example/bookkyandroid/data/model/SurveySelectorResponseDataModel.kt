package com.example.bookkyandroid.data.model;

import com.google.gson.annotations.SerializedName

data class SurveySelectorResponseDataModel (
    @SerializedName("tag")
    val tag : ArrayList<SurveySelectorItemDataModel>
    )
