package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class ReviewLikeResponseDataModel (
    @SerializedName("isLiked")
    val isLiked : Boolean
        )