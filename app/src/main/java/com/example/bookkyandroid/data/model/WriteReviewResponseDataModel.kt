package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class WriteReviewResponseDataModel (
    @SerializedName("review")
    val review : MyProfileReviewDataModel
        )