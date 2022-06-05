package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class WriteReviewBodyDataModel (
    @SerializedName("contents")
    val contents: String,
    @SerializedName("rating")
    val rating : Float
        )