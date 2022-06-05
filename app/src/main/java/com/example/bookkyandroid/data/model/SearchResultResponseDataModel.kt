package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class SearchResultResponseDataModel(
    @SerializedName("searchData")
    val searchData : ArrayList<SearchResultDataModel>,
    @SerializedName("total")
    val total : Int
)