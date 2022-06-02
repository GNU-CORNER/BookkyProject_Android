package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class PostFavoriteBookDataModel (
    @SerializedName("isFavorite")
    val isFavorite : Boolean
        )