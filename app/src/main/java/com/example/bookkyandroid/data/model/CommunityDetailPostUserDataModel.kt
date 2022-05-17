package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CommunityDetailPostUserDataModel(
    @SerializedName("nickname")
    val nickname : String?,
    @SerializedName("thumbnail")
    val thumbnail : String?

    )
