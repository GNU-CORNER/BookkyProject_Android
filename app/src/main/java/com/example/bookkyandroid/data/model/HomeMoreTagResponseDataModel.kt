package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeMoreTagResponseDataModel (
    @SerializedName("bookList")
    val bookList : ArrayList<HomeBookListDataModel?>,
    @SerializedName("nickname")
    val nickname : String?
    )