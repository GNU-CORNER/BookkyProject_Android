package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class UpdateProfileBodyDataModel(
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("images")
    val images : String
)