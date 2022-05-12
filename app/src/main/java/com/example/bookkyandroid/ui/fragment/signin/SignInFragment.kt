package com.example.bookkyandroid.ui.fragment.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.UserSignInBody
import com.example.bookkyandroid.data.model.UserSignInResponse
import com.example.bookkyandroid.databinding.FragmentSigninBinding
import com.example.bookkyandroid.ui.activity.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInFragment : BaseFragment<FragmentSigninBinding>(FragmentSigninBinding::bind, R.layout.fragment_signin) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = this
        val bookkyService = RetrofitManager.getInstance().bookkyService
        binding.loginButtonSignIn!!.setOnClickListener {
            signIn(
                binding.loginEditTextEmailInput!!.text.toString(),
                binding.loginEditTextPasswordInput!!.text.toString(),
                bookkyService,
                context!!
            )
        }
        binding.loginImageViewNaverImage!!.setOnClickListener{socialNaverSignIn()}
        binding.loginImageViewKakaoImage!!.setOnClickListener{socialKaKaoSignIn()}
        binding.loginImageViewGoogleImage!!.setOnClickListener{
            socialGoogleSignIn()
        }
        binding.loginTextViewSignUpTitle!!.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signupFragment)
        }
        binding.loginTextViewForgotTitle!!.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_findPwFragment)
        }
    }

}
private fun successLogin(view : SignInFragment, data : UserSignInResponse){
    CoroutineScope(Dispatchers.IO).launch {
        val accessToken = data.singInResult.access_token
        val refreshToken = data.singInResult.refresh_token
        ApplicationClass.getInstance().getDataStore().setAccessToken(accessToken)
        ApplicationClass.getInstance().getDataStore().setRefreshToken(refreshToken)
    }
    view.findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
}

private fun signIn(email: String, password: String, bookkyService: BookkyService, activity: SignInFragment){
    val bodyParameter = UserSignInBody(email, password)
    bookkyService.signIn(bodyParameter)
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
                    successLogin(activity, it)
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
