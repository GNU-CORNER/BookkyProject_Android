package com.example.bookkyandroid.ui.activity.login

import android.os.Bundle
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseActivity
import com.example.bookkyandroid.databinding.ActivityLoginBinding
import com.example.bookkyandroid.ui.fragment.signin.SignInFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(
            R.id.login_fragmentContainerView_fragmentLayer,
            SignInFragment()
        )
        transaction.commit()

    }

}
