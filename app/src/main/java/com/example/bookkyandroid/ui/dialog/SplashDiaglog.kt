package com.example.bookkyandroid.util

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.Window
import android.view.WindowManager
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.config.TokenManager
import com.example.bookkyandroid.data.model.AuthRefreshResponseDataModel
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.databinding.DialogSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep

class SplashDiaglog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFFFF")))
        window!!.setDimAmount(0.2f)
        window!!.setLayout(MATCH_PARENT, MATCH_PARENT)
        window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) //배경이 투명해지지 않도록
        RetrofitManager.refresh_token()
        ObjectAnimator.ofFloat(binding.splashImageView, "translationY", -200.0F).apply {
            val yOnbject = binding.splashImageView.y
            if(yOnbject == yOnbject - 100.0F)binding.splashProgressBar.visibility = View.VISIBLE
            duration = 2000
            start()
        }
    }
    override fun show() {
        if(!this.isShowing) super.show()
    }
}