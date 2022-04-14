package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class UserEmailResponseDataModel(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("result")
    val result : UserEmailResultDataModel,
    @SerializedName("errorMessage")
    val errorMessage : String
)