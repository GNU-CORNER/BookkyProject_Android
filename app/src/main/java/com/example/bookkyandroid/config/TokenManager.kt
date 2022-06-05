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
    lateinit var access_token : String
    lateinit var refresh_token : String

    companion object{

        @Volatile private var instance: TokenManager? = null

        @JvmStatic fun getInstance(): TokenManager =
            instance ?: synchronized(this){
                instance ?: TokenManager().also {
                    instance = it
                }
            }
    }
    fun getToken(){
        CoroutineScope(Dispatchers.IO).launch {
            access_token = ApplicationClass.getInstance().getDataStore().accessToken.first()
            refresh_token = ApplicationClass.getInstance().getDataStore().refreshToken.first()
            Log.d("getToken", access_token)
            Log.d("getToken", refresh_token)
        }
    }
}