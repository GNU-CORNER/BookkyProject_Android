package com.example.bookkyandroid.ui.fragment.findpw

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.InitPasswordBodyDataModel
import com.example.bookkyandroid.databinding.FragmentFindpwNewpwBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindPwNewPwFragment: BaseFragment<FragmentFindpwNewpwBinding>(FragmentFindpwNewpwBinding::bind, R.layout.fragment_findpw_newpw) {
    var flag = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = ApplicationClass.getInstance().getRetrofit()
        val email = arguments?.getString("email")
        binding.findpwNewEditTextInputPwCheck.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.findpwNewButtonFinishButton.setBackgroundResource(R.drawable.background_round_primary)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.findpwNewButtonFinishButton.setOnClickListener {
            if(!flag){
                if(binding.findpwNewEditTextInputPw.text.length>=8){
                    if(binding.findpwNewEditTextInputPw.text.toString().equals(binding.findpwNewEditTextInputPwCheck.text.toString())){
                        initPassword(email.toString(),binding.findpwNewEditTextInputPwCheck.text.toString(), bookkyService)
                    }
                    else {
                        showCustomToast("비밀번호가 일치하지 않습니다.")
                    }
                }
                else{
                    showCustomToast("비밀번호는 8자리 이상이어야 합니다.")
                }
            }
        }
    }
    private fun successInitialized(){
        findNavController().navigate(R.id.action_global_signInFragment)
    }
    private fun initEditText(){
        binding.findpwNewEditTextInputPw.setText("")
        binding.findpwNewEditTextInputPwCheck.setText("")
    }
    private fun initPassword(email:String, password:String, bookkyService: BookkyService){
        val bodyParameter = InitPasswordBodyDataModel(email,password)
        bookkyService.initPassword(bodyParameter)
            .enqueue(object : Callback<BaseResponse<String?>> {
                override fun onFailure(call: Call<BaseResponse<String?>>, t: Throwable) {
                    Log.d("LoginAPI", t.toString())
                }

                override fun onResponse(call: Call<BaseResponse<String?>>, signUpResponse: Response<BaseResponse<String?>>) {
                    if (signUpResponse.isSuccessful.not()) {
                        FindPwNewPwFragment().showCustomToast("비밀번호를 다시 입력해주세요.")
                        initEditText()
                        return
                    }
                    signUpResponse.body()?.let {
                        successInitialized()
                    }
                }
            })
    }
}