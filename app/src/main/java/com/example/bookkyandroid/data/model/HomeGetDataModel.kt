package com.example.bookkyandroid.data.model

import retrofit2.http.Header

data class HomeGetDataModel(
    @Header("access-token")
    val access_token :String
)