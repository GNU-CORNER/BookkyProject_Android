package com.example.bookkyandroid.ui.fragment.signup

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.core.os.bundleOf
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
import java.util.regex.Pattern
import kotlin.concurrent.timer

class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::bind, R.layout.fragment_signup) {
    private var flag =false
    private var flag2=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = ApplicationClass.getInstance().getRetrofit()

        binding.signupButtonCodeCheckButton.setBackgroundResource(R.drawable.background_round_dark)
        binding.signupButtonSignUpButton!!.setOnClickListener{
            if(flag2&&flag){
                val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"
                val pattern = Pattern.compile(pwPattern)
                if(binding.signupEditTextPasswordInput.text.length >= 8){
                    if(binding.signupEditTextPasswordCheckInput.text.toString().equals(binding.signupEditTextPasswordInput.text.toString())){
                        if (pattern.matcher(binding.signupEditTextPasswordCheckInput.text.toString()).matches()){
                            signUp(
                                binding.signupEditTextEmailInput!!.text.toString(),
                                binding.signupEditTextPasswordInput!!.text.toString(),
                                binding.signupEditTextNicknameInput!!.text.toString(),
                                bookkyService,
                                this
                            )
                        }
                        else{
                            showCustomToast("??????????????? ????????? ????????? ?????? ??????????????? ?????????.")
                        }
                    }
                    else{
                        showCustomToast("??????????????? ???????????? ????????????.")
                    }
                }
                else{
                    showCustomToast("??????????????? 8?????? ??????????????? ?????????.")
                }
            }
            else{
                showCustomToast("????????? ????????? ????????????.")
            }
        }
        binding.signupButtonCodeSendButton!!.setOnClickListener{
            if(!flag2){
                val pattern: Pattern = Patterns.EMAIL_ADDRESS
                if(pattern.matcher(binding.signupEditTextEmailInput.text.toString()).matches()){
                    sendTo(
                        binding.signupEditTextEmailInput.text.toString(),
                        bookkyService
                    )
                    flag2 = true
                }
                else{
                    showCustomToast("????????? ????????? ????????????.")
                }
            }
        }
        binding.signupButtonCodeCheckButton.setOnClickListener {
            if(flag) {
                if (binding.signupEditTextCodeInput.text.toString().length==8){
                    checkCode(
                        binding.signupEditTextEmailInput.text.toString(),
                        binding.signupEditTextCodeInput.text.toString(),
                        bookkyService
                    )
                }
                else{
                    showCustomToast("????????? 8?????? ?????????.")
                }
            }
        }
    }
    private fun successSignUp(view : SignupFragment, data : UserSignUpResponse){
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = data.singUpResult.access_token
            val refreshToken = data.singUpResult.refresh_token
            ApplicationClass.getInstance().getDataStore().setAccessToken(accessToken)
            ApplicationClass.getInstance().getDataStore().setRefreshToken(refreshToken)
            ApplicationClass.getInstance().recreateRetrofit()
        }
        view.findNavController().navigate(R.id.action_signupFragment_to_surveyFragment)
    }
    private fun successToSend(){
        showCustomToast("??????????????? ??????????????????.")
        binding.signupEditTextEmailInput.setTextColor(Color.GRAY)
        binding.signupButtonCodeCheckButton.setBackgroundResource(R.drawable.background_round_primary)
        binding.signupButtonCodeSendButton.setBackgroundResource(R.drawable.background_round_dark)
        binding.signupEditTextEmailInput.isEnabled = false
        flag = true
    }
    private fun successToCheck(){
        showCustomToast("????????? ?????????????????????.")
        binding.signupButtonCodeCheckButton.setBackgroundResource(R.drawable.background_round_dark)
        binding.signupEditTextCodeInput.isEnabled = false
    }
    private fun signUp(email: String, password: String, nickname: String, bookkyService: BookkyService, activity: SignupFragment){
        val bodyParameter = UserSignUpBody(email, password, nickname, 0)
        bookkyService.signUp(bodyParameter)
            .enqueue(object : Callback<UserSignUpResponse> {
                override fun onFailure(call: Call<UserSignUpResponse>, t: Throwable) {
                    Log.d("LoginAPI", t.toString())
                }

                override fun onResponse(call: Call<UserSignUpResponse>, signUpResponse: Response<UserSignUpResponse>) {
                    if (signUpResponse.isSuccessful.not()) {
                        SignupFragment().showCustomToast("?????? ???????????? ??????????????????.")
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
                    if (response.body()!!.success.not()){
                        SignupFragment().showCustomToast("?????? ???????????? ??????????????????.")

                        return
                    }
                    response.body()?.let {
                        Log.d("SignupEmail", it.success.toString())
                        successToSend()
                    }
                }
            })
    }
    private fun checkCode(email:String, code : String ,bookkyService: BookkyService){
        bookkyService.checkAuthenticationCode(UserAuthenticationCode(email, code))
            .enqueue(object : Callback<BaseResponse<String?>>{
                override fun onFailure(call: Call<BaseResponse<String?>>, t: Throwable) {
                    Log.d("SignupEmail",t.toString())
                }

                override fun onResponse(
                    call: Call<BaseResponse<String?>>,
                    response: Response<BaseResponse<String?>>
                ) {
                    if (response.body()!!.success.not()){
                        SignupFragment().showCustomToast("??????????????? ???????????? ????????????.")

                        return
                    }
                    response.body()?.let {
                        successToCheck()
                    }
                }
            })

    }
}
