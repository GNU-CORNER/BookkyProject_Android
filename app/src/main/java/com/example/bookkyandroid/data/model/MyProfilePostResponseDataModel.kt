package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class MyProfilePostResponseDataModel(
    @SerializedName("communityList")
    val communityList : ArrayList<MyProfilePostDataModel>
)