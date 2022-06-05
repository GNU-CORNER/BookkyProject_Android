package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class WriteBookSearchResponseDataModel (
    @SerializedName("searchData")
    val searchData : ArrayList<WriteBookSearchDataModel>,
    @SerializedName("total")
    val total : Int

)