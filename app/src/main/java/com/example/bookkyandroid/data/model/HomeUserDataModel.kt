package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class HomeUserDataModel (
    @SerializedName("UID")
    val UID : Int,
    @SerializedName("tag_array")
    val tag_array : ArrayList<String>,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("thumbnail")
    val thumbnail : String
    )
