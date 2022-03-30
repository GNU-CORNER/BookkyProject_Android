package com.example.bookkyandroid.ui.activity.main

import android.os.Bundle
import android.util.Log
import com.example.bookkyandroid.config.BaseActivity
import com.example.bookkyandroid.data.model.UserSignInBody
import com.example.bookkyandroid.data.model.UserSignInResponse
import com.example.bookkyandroid.data.model.UserSignUpBody
import com.example.bookkyandroid.data.model.UserSignUpResponse
import com.example.bookkyandroid.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.loginButtonSignIn!!.setOnClickListener {
            signIn(
                binding.loginEditTextEmailInput!!.text.toString(),
                binding.loginEditTextPasswordInput!!.text.toString(),
                retrofit
            )
        }
        binding.loginImageViewNaverImage!!.setOnClickListener{socialNaverSignIn()}
        binding.loginImageViewKakaoImage!!.setOnClickListener{socialKaKaoSignIn()}
        binding.loginImageViewGoogleImage!!.setOnClickListener{
            socialGoogleSignIn()
        }
        binding.loginTextViewSignUpTitle!!.setOnClickListener {
            signUp()
        }
    }
    interface LoginPostCaller {
        @POST("/v1/test1")
        fun signIn(
            @Body userSignInbody : UserSignInBody
        ): Call<UserSignInResponse>
    }
    interface SignUpPostCaller{
        @POST("/v1/test1")
        fun signUp(
            @Body userSignUpBody : UserSignUpBody
        ): Call<UserSignUpResponse>
    }
}


private fun signIn(email: String, password: String, retrofit : Retrofit){
    val userService = retrofit.create(LoginActivity.LoginPostCaller::class.java)
    val bodyParameter = UserSignInBody(email, password)
    userService.signIn(bodyParameter)
        .enqueue(object : Callback<UserSignInResponse> {
            override fun onFailure(call: Call<UserSignInResponse>, t: Throwable) {
                Log.d("LoginAPI", t.toString())
            }

            override fun onResponse(call: Call<UserSignInResponse>, signInResponse: Response<UserSignInResponse>) {
                if (signInResponse.isSuccessful.not()) {
                    return
                }
                signInResponse.body()?.let {
                    Log.d("LoginAPI", it.toString())
                    Log.d("LoginAPI", it.singInResult.toString())

                }
            }
        })
}
private fun socialKaKaoSignIn(){

}
private fun socialNaverSignIn(){

}
private fun socialGoogleSignIn(){

}
private fun signUp(){

}
private fun forgotIt(){

}