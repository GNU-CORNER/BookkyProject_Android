package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class MyReviewResponseDataModel(
    @SerializedName("reviewList")
    val reviewList : ArrayList<MyProfileReviewDataModel>
)