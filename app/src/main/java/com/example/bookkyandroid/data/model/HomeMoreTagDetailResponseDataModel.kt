package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeMoreTagDetailResponseDataModel(
    @SerializedName("bookList")
    val bookList : HomeBookListDataModel
)