package com.example.bookkyandroid.ui.fragment.signin

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
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.UserAuthenticationCode
import com.example.bookkyandroid.data.model.UserEmailResponseDataModel
import com.example.bookkyandroid.databinding.FragmentFindpwBinding
import com.example.bookkyandroid.ui.fragment.signup.SignupFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class FindPwFragment : BaseFragment<FragmentFindpwBinding>(FragmentFindpwBinding::bind, R.layout.fragment_findpw) {
    var flag = false
    var flag2 = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = RetrofitManager.getInstance().bookkyService
        binding.findpwButtonCallCode.setOnClickListener {
            if (!flag2){
                val pattern: Pattern = Patterns.EMAIL_ADDRESS
                if(pattern.matcher(binding.findpwEditTextEmailInput.text.toString()).matches()){
                    binding.findpwButtonCallCode.setBackgroundResource(R.drawable.background_round_dark)
                    binding.findpwButtonCheckCode.setBackgroundResource(R.drawable.background_round_primary)
                    sendTo(
                        binding.findpwEditTextEmailInput.text.toString(),
                        bookkyService
                    )
                    flag2=true
                }
                else{
                    showCustomToast("이메일 형식이 아닙니다.")
                }
            }
        }
        binding.findpwButtonNext.setOnClickListener{
            if(flag && flag2){
                val bundle = bundleOf("email" to binding.findpwEditTextEmailInput.text.toString())
                findNavController().navigate(R.id.action_findPwFragment_to_findPwNewPwFragment, bundle)
            }
        }
        binding.findpwButtonCheckCode.setOnClickListener {
            if(!flag && flag2){
                binding.findpwButtonCheckCode.setBackgroundResource(R.drawable.background_round_dark)
                checkCode(binding.findpwEditTextEmailInput.text.toString(), binding.findpwEditTextCode.text.toString(), bookkyService)
            }
        }
    }
    private fun successSendTo(){
        binding.findpwEditTextEmailInput.isEnabled = false
    }
    private fun successCheck(){
        binding.findpwButtonNext.setBackgroundResource(R.drawable.background_round_primary)
        flag = true
        binding.findpwEditTextCode.isEnabled = false
    }
    private fun sendTo(email : String, bookkyService: BookkyService){
        bookkyService.emailAuth(email)
            .enqueue(object : Callback<UserEmailResponseDataModel> {
                override fun onFailure(call: Call<UserEmailResponseDataModel>, t: Throwable) {
                    Log.d("SignupEmail",t.toString())
                }

                override fun onResponse(
                    call: Call<UserEmailResponseDataModel>,
                    response: Response<UserEmailResponseDataModel>
                ) {
                    if (response.body()!!.success.not()){
                        showCustomToast("이미 존재하는 이메일입니다.")

                        return
                    }
                    response.body()?.let {
                        successSendTo()
                        showCustomToast("인증코드를 전송했습니다.")
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
                        showCustomToast("인증번호가 일치하지 않습니다.")

                        return
                    }
                    response.body()?.let {
                        successCheck()
                    }
                }
            })

    }
}