package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeResultResponseDataModel(
    @SerializedName("bookList")
    val bookList : ArrayList<HomeBookListDataModel>?,
    @SerializedName("communityList")
    val communityList : ArrayList<HomeCommunityDataModel>?,
    @SerializedName("userData")
    val userData : HomeUserDataModel?
    )