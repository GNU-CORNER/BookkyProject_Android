package com.example.bookkyandroid.config

import android.util.Log
import com.example.bookkyandroid.data.model.AuthRefreshResponseDataModel
import com.example.bookkyandroid.data.model.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenManager {
    companion object{
        @Volatile private var instance: TokenManager? = null

        @JvmStatic fun getInstance(): TokenManager =
            instance ?: synchronized(this){
                instance ?: TokenManager().also {
                    instance = it
                }
            }

    }
    var accessToken : String = ""
    var refreshToken : String = ""
    fun refresh_token(bookkyService:BookkyService, accessToken:String, refreshToken:String){
        Log.d("asd", accessToken)
        Log.d("asd",refreshToken)
        bookkyService.refreshToken(accessToken,refreshToken)
            .enqueue(object : Callback<BaseResponse<AuthRefreshResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, response: Response<BaseResponse<AuthRefreshResponseDataModel>>) {
                    if (response.isSuccessful.not()) {
                        Log.d("refresh", response.toString())
                        return
                    }
                    response.body()?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            ApplicationClass.getInstance().getDataStore()
                                .setAccessToken(accessToken)
                        }
                    }
                }
            })
    }
}