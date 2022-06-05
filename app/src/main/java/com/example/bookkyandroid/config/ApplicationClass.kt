package com.example.bookkyandroid.config

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.bookkyandroid.util.LoadingDialog
import com.example.bookkyandroid.util.SplashDiaglog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

// 앱이 실행될때 1번만 실행이 됩니다.
class ApplicationClass : Application() {
    private lateinit var dataStore : DataStoreManager
    private lateinit var bookkyService: BookkyService
    lateinit var mLoadingDialog: LoadingDialog
    lateinit var splashDiaglog: SplashDiaglog
    // 테스트 서버 주소
    // val API_URL = "http://dev-api.test.com/"


    // 코틀린의 전역변수 문법
    companion object {
        private lateinit var applicationClass: ApplicationClass
        fun getInstance() : ApplicationClass{

            return applicationClass
        }
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        // JWT Token Header 키 값

    }

    // 앱이 처음 생성되는 순간, SP를 새로 만들어주고, 레트로핏 인스턴스를 생성합니다.
    override fun onCreate() {
        super.onCreate()
        // 레트로핏 인스턴스 생성
        applicationClass = this
        dataStore = DataStoreManager(this)
        bookkyService = BookkyService.create()
        TokenManager.getInstance().getToken()
    }
    fun getDataStore() : DataStoreManager = dataStore
    fun getRetrofit() = bookkyService
    fun recreateRetrofit() : BookkyService{
        TokenManager.getInstance().getToken()
        bookkyService = BookkyService.create()
      return bookkyService
    }
    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }
    fun showSplashDiaglog(context:Context){
        splashDiaglog = SplashDiaglog(context)
        splashDiaglog.show()
    }
    fun dismissSplashDialog(){
        if(splashDiaglog.isShowing){
            splashDiaglog.dismiss()
        }
    }
    // 띄워 놓은 로딩 다이얼로그를 없앰.
    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
}