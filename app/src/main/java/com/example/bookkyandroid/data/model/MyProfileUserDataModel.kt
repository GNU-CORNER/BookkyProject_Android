package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class MyProfileUserDataModel (
    @SerializedName("userTagList")
    val userTagList : ArrayList<TagDataResponseDataModel>?,
    @SerializedName("nickname")
    val nickname : String?,
    @SerializedName("userThumbnail")
    val userThumbnail : String?
    )
