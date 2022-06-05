package com.example.bookkyandroid.config
import android.util.Log
import com.example.bookkyandroid.data.model.AuthRefreshResponseDataModel
import com.example.bookkyandroid.data.model.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitManager {
    private const val BASE_URL = "http://203.255.3.144:8002"
    fun getApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun refresh_token(){

        bookkyService.refreshToken()
            .enqueue(object : Callback<BaseResponse<AuthRefreshResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, t: Throwable) {
                }
                override fun onResponse(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, response: Response<BaseResponse<AuthRefreshResponseDataModel>>) {
                    if (response.isSuccessful.not()) {
                        Log.d("refresh", response.toString())
                        return
                    }
                    response.body()?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            ApplicationClass.getInstance().getDataStore()
                                .setAccessToken(it.result.access_token)
                            TokenManager.getInstance().getToken()
                            ApplicationClass.getInstance().recreateRetrofit()
                        }

                    }
                }
            })
    }
    private fun provideOkHttpClient(
        interceptor: AppInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .run {
            addInterceptor(interceptor)
            build()
        }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain)
                : okhttp3.Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("access-token", TokenManager.getInstance().access_token)
                .addHeader("refresh-token", TokenManager.getInstance().refresh_token)
                .build()

            proceed(newRequest)
        }
    }
    val bookkyService : BookkyService = getApiClient().create(BookkyService::class.java)

}