package com.example.bookkyandroid.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager private constructor() {
    companion object{
        @Volatile private var instance: RetrofitManager? = null

        @JvmStatic fun getInstance(): RetrofitManager =
            instance ?: synchronized(this){
                instance ?: RetrofitManager().also {
                    instance = it
                }
            }
    }
    val retrofit = Retrofit.Builder()
        .baseUrl("http://203.255.3.144:8002")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val bookkyService : BookkyService = retrofit.create(BookkyService::class.java)

}