package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class MyProfileResponseDataModel(
    @SerializedName("userData")
    val userData : MyProfileUserDataModel,
    @SerializedName("favoriteBookList")
    val favoriteBookList : ArrayList<HomeBookDataModel>,
    @SerializedName("userPostList")
    val userPostList : ArrayList<MyProfilePostDataModel>,
    @SerializedName("userReviewList")
    val userReviewList : ArrayList<MyProfileReviewDataModel>
    )