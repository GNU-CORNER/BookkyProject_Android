package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName


data class UserSignInResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val singInResult: UserSingInResult,
    @SerializedName("errorMessage") val errorMessage: String
)
