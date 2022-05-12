package com.example.bookkyandroid.ui.fragment.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentSignupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::bind, R.layout.fragment_signup) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isSpotlight = false
        binding.signupButtonCodeSendButton.visibility = View.GONE
        val bookkyService = RetrofitManager.getInstance().bookkyService
        binding.signupButtonSignUpButton!!.setOnClickListener{
            signUp(
                binding.signupEditTextEmailInput!!.text.toString(),
                binding.signupEditTextPasswordInput!!.text.toString(),
                binding.signupEditTextNicknameInput!!.text.toString(),
                bookkyService,
                this
            )
        }
        binding.signupEditTextEmailInput!!.setOnClickListener{
            if (!isSpotlight){
                binding.signupButtonCodeSendButton.visibility = View.VISIBLE
            }
        }
        binding.signupButtonCodeSendButton!!.setOnClickListener{
            sendTo(
                binding.signupEditTextEmailInput.text.toString(),
                bookkyService
            )
        }



    }
}
private fun successSignUp(view : SignupFragment, data : UserSignUpResponse){
    CoroutineScope(Dispatchers.IO).launch {
        val accessToken = data.singUpResult.access_token
        val refreshToken = data.singUpResult.refresh_token
        ApplicationClass.getInstance().getDataStore().setAccessToken(accessToken)
        ApplicationClass.getInstance().getDataStore().setRefreshToken(refreshToken)
    }
    view.findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
}
private fun signUp(email: String, password: String, nickname: String, bookkyService: BookkyService, activity: SignupFragment){
    val bodyParameter = UserSignUpBody(email, password, nickname)
    bookkyService.signUp(bodyParameter)
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
                    successSignUp(activity, it)
                }
            }
        })
}
private fun sendTo(email : String, bookkyService: BookkyService){
    bookkyService.sendTo(email)
        .enqueue(object : Callback<UserEmailResponseDataModel>{
            override fun onFailure(call: Call<UserEmailResponseDataModel>, t: Throwable) {
                Log.d("SignupEmail",t.toString())
            }

            override fun onResponse(
                call: Call<UserEmailResponseDataModel>,
                response: Response<UserEmailResponseDataModel>
            ) {
                if (response.isSuccessful.not()){
                    return
                }
                response.body()?.let {
                    Log.d("SignupEmail", it.success.toString())
                }
            }
        })
}