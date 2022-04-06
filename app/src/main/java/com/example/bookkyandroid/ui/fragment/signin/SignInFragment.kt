package com.example.bookkyandroid.ui.fragment.signin

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.UserSignInBody
import com.example.bookkyandroid.data.model.UserSignInResponse
import com.example.bookkyandroid.data.model.UserSignUpBody
import com.example.bookkyandroid.data.model.UserSignUpResponse
import com.example.bookkyandroid.databinding.FragmentSigninBinding
import com.example.bookkyandroid.ui.fragment.findpw.FindPwFragment
import com.example.bookkyandroid.ui.fragment.signup.SignupFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class SignInFragment : BaseFragment<FragmentSigninBinding>(FragmentSigninBinding::bind, R.layout.fragment_signin) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val transaction =parentFragmentManager.beginTransaction()
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
            transaction.replace(R.id.login_fragmentContainerView_fragmentLayer, SignupFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.loginTextViewForgotTitle!!.setOnClickListener {
            transaction.replace(R.id.login_fragmentContainerView_fragmentLayer, FindPwFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    interface LoginPostCaller {
        @POST("/v1/user/signin")
        fun signIn(
            @Body userSignInbody : UserSignInBody
        ): Call<UserSignInResponse>
    }
}


private fun signIn(email: String, password: String, retrofit : Retrofit){
    val userService = retrofit.create(SignInFragment.LoginPostCaller::class.java)
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
