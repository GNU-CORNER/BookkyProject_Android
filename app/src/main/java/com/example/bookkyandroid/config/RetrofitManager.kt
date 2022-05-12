package com.example.bookkyandroid.config

import android.util.Log
import com.example.bookkyandroid.data.model.AuthRefreshResponseDataModel
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.HomeResponseDataModel
import com.example.bookkyandroid.ui.fragment.home.HomeFragment
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager {
    companion object{
        @Volatile private var instance: RetrofitManager? = null

        @JvmStatic fun getInstance(): RetrofitManager =
            instance ?: synchronized(this){
                instance ?: RetrofitManager().also {
                    instance = it
                }
            }
        val access_token = ApplicationClass.getInstance().getDataStore().accessToken
        val refresh_token = ApplicationClass.getInstance().getDataStore().refreshToken
    }
    private fun initRetrofitInstance(): Retrofit {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }


    val bookkyService : BookkyService = initRetrofitInstance().create(BookkyService::class.java)

    fun refresh_token(){
        val dataStoreInstance =ApplicationClass.getInstance().getDataStore()

        bookkyService.refreshToken(access_token.toString(), refresh_token.toString())
            .enqueue(object : Callback<BaseResponse<AuthRefreshResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, response: Response<BaseResponse<AuthRefreshResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        var accessToken = it.result.access_token

                        runBlocking { dataStoreInstance.setAccessToken(accessToken) }

                    }
                }
            })

    }
}