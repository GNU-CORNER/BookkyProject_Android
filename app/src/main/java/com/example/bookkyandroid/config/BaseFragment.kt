package com.example.bookkyandroid.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.bookkyandroid.ui.dialog.MoreConfigurationBottomSheetDialog
import com.example.bookkyandroid.ui.dialog.PostBookListBottomSheetDialog
import com.example.bookkyandroid.util.LoadingDialog
import com.example.bookkyandroid.util.SplashDiaglog


// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    lateinit var splashDiaglog: SplashDiaglog

    protected val binding get() = _binding!!
    lateinit var mLoadingDialog: LoadingDialog
    lateinit var bottomSheet : PostBookListBottomSheetDialog
    lateinit var bottomSheetMoreConfiguration : MoreConfigurationBottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }
    fun showSplashDiaglog(context:Context){
        splashDiaglog = SplashDiaglog(context)
        splashDiaglog.show()
    }
    fun dismissSplashDialog(){
        if(splashDiaglog.isShowing){
            splashDiaglog.dismiss()
        }
    }
    // 띄워 놓은 로딩 다이얼로그를 없앰.
    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
}