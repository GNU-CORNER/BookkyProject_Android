package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName


data class UserSignUpResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val singUpResult: UserSingUpResult,
    @SerializedName("errorMessage") val errorMessage: String

)
