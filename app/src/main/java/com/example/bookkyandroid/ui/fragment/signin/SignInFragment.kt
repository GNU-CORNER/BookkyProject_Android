package com.example.bookkyandroid.ui.fragment.signin

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.UserSignInBody
import com.example.bookkyandroid.data.model.UserSignInResponse
import com.example.bookkyandroid.data.model.UserSingInResult
import com.example.bookkyandroid.databinding.FragmentSigninBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignInFragment : BaseFragment<FragmentSigninBinding>(FragmentSigninBinding::bind, R.layout.fragment_signin) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = ApplicationClass.getInstance().getRetrofit()
        binding.loginButtonSignIn!!.setOnClickListener {
            val pattern: Pattern = Patterns.EMAIL_ADDRESS
            if (pattern.matcher(binding.loginEditTextEmailInput.text.toString()).matches()){
//                if(binding.loginEditTextPasswordInput.text.length >= 8){  //test때문에 일단은 주석처리
                    signIn(
                        binding.loginEditTextEmailInput!!.text.toString(),
                        binding.loginEditTextPasswordInput!!.text.toString(),
                        bookkyService,
                    )
//                }
//                else{
//                    showCustomToast("비밀번호는 8자리 이상이어야 합니다.")
//                }
            }
            else{
                showCustomToast("이메일 형식이 아닙니다.")
            }
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
    fun failLogin(){
        showCustomToast("이메일 혹은 비밀번호가 틀렸습니다")
    }

    private fun successLogin(data : UserSingInResult){
        //로그인 성공했을때, datastore에 토큰을 저장하는 부분에서 BACK THREAD에서 저장 함수 호출 Coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = data.access_token
            val refreshToken = data.refresh_token
            ApplicationClass.getInstance().getDataStore().setAccessToken(accessToken)
            ApplicationClass.getInstance().getDataStore().setRefreshToken(refreshToken)
            ApplicationClass.getInstance().recreateRetrofit()
        }
        val bundle = bundleOf("type" to "key")
        findNavController().navigate(R.id.action_signInFragment_to_homeFragment, bundle)

    }

    private fun signIn(email: String, password: String, bookkyService: BookkyService){
        val bodyParameter = UserSignInBody(email, password)
        bookkyService.signIn(bodyParameter)
            .enqueue(object : Callback<UserSignInResponse> {
                override fun onFailure(call: Call<UserSignInResponse>, t: Throwable) {
                    failLogin()
                }

                override fun onResponse(call: Call<UserSignInResponse>, signInResponse: Response<UserSignInResponse>) {
                    if (signInResponse.isSuccessful.not()) {
                        return
                    }
                    signInResponse.body()?.let {
                        successLogin(it.singInResult)
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
}

