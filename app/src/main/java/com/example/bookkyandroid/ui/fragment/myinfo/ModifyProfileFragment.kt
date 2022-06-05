package com.example.bookkyandroid.ui.fragment.myinfo

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.databinding.FragmentModifyProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ModifyProfileFragment : BaseFragment<FragmentModifyProfileBinding>(
    FragmentModifyProfileBinding::bind, R.layout.fragment_modify_profile) {
    val PERMISSION_Album = 101 // 앨범 권한 처리
    val REQUEST_STORAGE = 102
    private var flag = false
    private var imageUri : String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image = arguments?.getString("image22")
        Log.d("image", image.toString())
        val nickname = arguments?.getString("nickname")
        Glide.with(this)
            .load(image.toString())
            .override(150, 150)
            .diskCacheStrategy(DiskCacheStrategy.NONE )
            .skipMemoryCache(true)
            .into(binding.imageView3)
        binding.editTextTextPersonName3.setText(nickname.toString())
        binding.button2.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val bookkyService = ApplicationClass.getInstance().getRetrofit()
                if (flag) {
                    runBlocking {
                        updateProfile(
                            bookkyService, UpdateProfileBodyDataModel(
                                binding.editTextTextPersonName3.text.toString(),
                                "data:image/jpeg;base64," + imageUri
                            )
                        )
                    }
                } else {
                    showCustomToast("변경사항이 없습니다.")
                }
            }
        }
        binding.editTextTextPersonName3.addTextChangedListener{
            binding.button2.setBackgroundResource(R.drawable.background_round_primary)
            binding.button2.setTextColor(Color.WHITE)
            flag = true
        }

        binding.imageButton6.setOnClickListener {
            requirePermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_Album
            )
        }
    }
    private fun successToUpdate(){
        Log.d("pop", "stack Back")
        findNavController().popBackStack()
    }
    private fun getBase64ForUriAndPossiblyCrash(uri: Uri): String {
        var imageBase64 : String = ""
        try {
            val bytes = requireActivity().contentResolver.openInputStream(uri)!!.readBytes()

            imageBase64 = encodeToString(bytes, DEFAULT)
        } catch (error: IOException) {
            error.printStackTrace() // This exception always occurs
        }
        return imageBase64
    }
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            // isAllPermissionsGranted : 권한이 모두 승인 되었는지 여부 저장
            // all 메서드를 사용하면 배열 속에 들어 있는 모든 값을 체크할 수 있다.
            val isAllPermissionsGranted =
                permissions.all { checkSelfPermission(requireContext(),it) == PermissionChecker.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
            }
        }
    }
    /** 사용자가 권한을 승인하거나 거부한 다음에 호출되는 메서드
     * @param requestCode 요청한 주체를 확인하는 코드
     * @param permissions 요청한 권한 목록
     * @param grantResults 권한 목록에 대한 승인/미승인 값, 권한 목록의 개수와 같은 수의 결괏값이 전달된다.
     * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    private fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERMISSION_Album -> openGallery()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERMISSION_Album -> showCustomToast("저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.")
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_STORAGE)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_STORAGE -> {
                    data?.data?.let { uri ->
                        binding.imageView3.setImageURI(uri)
                        binding.button2.setBackgroundResource(R.drawable.background_round_primary)
                        binding.button2.setTextColor(Color.WHITE)
                        flag = true
                        imageUri = getBase64ForUriAndPossiblyCrash(uri)
                    }
                }
            }
        }
    }
    private fun updateProfile(bookkyService: BookkyService, body : UpdateProfileBodyDataModel){
        bookkyService.updateMyProfile(body)
            .enqueue(object : Callback<BaseResponse<ModifyProfileResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<ModifyProfileResponseDataModel>>, t: Throwable) {
                    Log.d("api", "test")
                }

                override fun onResponse(call: Call<BaseResponse<ModifyProfileResponseDataModel>>, response: Response<BaseResponse<ModifyProfileResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        successToUpdate()
                    }
                }
            })
    }

}