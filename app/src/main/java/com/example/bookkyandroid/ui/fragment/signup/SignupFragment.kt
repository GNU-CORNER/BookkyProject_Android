package com.example.bookkyandroid.ui.fragment.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.UserSignUpBody
import com.example.bookkyandroid.data.model.UserSignUpResponse
import com.example.bookkyandroid.databinding.FragmentSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::bind, R.layout.fragment_signup) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        binding.signupButtonSignUpButton!!.setOnClickListener{
            signUp(
                binding.signupEditTextEmailInput!!.text.toString(),
                binding.signupEditTextPasswordInput!!.text.toString(),
                binding.signupEditTextNicknameInput!!.text.toString(),
                retrofit
            )
        }

    }
    interface SignUpPostCaller{
        @POST("/v1/user/signup")
        fun signUp(
            @Body userSignUpBody : UserSignUpBody
        ): Call<UserSignUpResponse>
    }
}
private fun signUp(email: String, password: String, nickname: String,retrofit : Retrofit){
    val userService = retrofit.create(SignupFragment.SignUpPostCaller::class.java)
    val bodyParameter = UserSignUpBody(email, password, nickname)
    userService.signUp(bodyParameter)
        .enqueue(object : Callback<UserSignUpResponse> {
            override fun onFailure(call: Call<UserSignUpResponse>, t: Throwable) {
                Log.d("LoginAPI", t.toString())
            }

            override fun onResponse(call: Call<UserSignUpResponse>, signUpResponse: Response<UserSignUpResponse>) {
                if (signUpResponse.isSuccessful.not()) {
                    return
                }
                signUpResponse.body()?.let {
                    Log.d("LoginAPI", it.toString())
                    Log.d("LoginAPI", it.singUpResult.toString())

                }
            }
        })
}