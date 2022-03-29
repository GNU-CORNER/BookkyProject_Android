package com.example.bookkyandroid.ui.activity.main

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.bookkyandroid.config.BaseActivity
import com.example.bookkyandroid.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private var loginButton : Button? = null
    private var emailInput : EditText? = null
    private var passwordInput : EditText? = null
    private var signupText : TextView? = null
    private var forgotText : TextView? = null
    private var naverImage : ImageView? = null
    private var kakaoImage : ImageView? = null
    private var googleImage : ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginButton = binding.loginButtonSignIn
        emailInput = binding.loginEditTextEmailInput
        passwordInput = binding.loginEditTextPasswordInput
        signupText = binding.loginTextViewSignUpTitle
        forgotText = binding.loginTextViewForgotTitle
        naverImage = binding.loginImageViewNaverImage
        kakaoImage = binding.loginImageViewKakaoImage
        googleImage = binding.loginImageViewGoogleImage
        loginButton!!.setOnClickListener {signIn(emailInput!!.text.toString(), passwordInput!!.text.toString())}
        naverImage!!.setOnClickListener{socialNaverSignIn()}
        kakaoImage!!.setOnClickListener{socialKaKaoSignIn()}
        googleImage!!.setOnClickListener{
            socialGoogleSignIn()
        }
        signupText!!.setOnClickListener {
            signUp()
        }
    }
}


private fun signIn(email: String, password: String){

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