package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class MyProfileUserDataModel (
    @SerializedName("userTagList")
    val userTagList : ArrayList<TagDataResponseDataModel>?,
    @SerializedName("nickname")
    val nickname : String?,
    @SerializedName("thumbnail")
    val thumbnail : String?
    )
