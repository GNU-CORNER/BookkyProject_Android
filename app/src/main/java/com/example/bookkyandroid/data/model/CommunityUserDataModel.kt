package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityUserDataModel(
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("thumbnail")
    val thumbnail : String
)
