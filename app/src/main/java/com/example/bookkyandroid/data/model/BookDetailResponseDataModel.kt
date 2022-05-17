package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class BookDetailResponseDataModel(
    @SerializedName("bookList")
    val bookList : BookDetailDataModel?,
    @SerializedName("isFavorite")
    val isFavorite : Boolean?,
    @SerializedName("reviewList")
    var reviewList : ArrayList<ReviewDataModel>?
)