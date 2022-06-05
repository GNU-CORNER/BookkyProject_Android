package com.example.bookkyandroid.ui.fragment.community

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.databinding.FragmentCommunityPostWriteBinding
import com.example.bookkyandroid.ui.adapter.WritePostBookAdapter
import com.example.bookkyandroid.ui.adapter.WritePostImageAdapter
import com.example.bookkyandroid.ui.dialog.PostBookListBottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class CommunityWriteFragment : BaseFragment<FragmentCommunityPostWriteBinding>(
    FragmentCommunityPostWriteBinding::bind, R.layout.fragment_community_post_write),getDatafromBottomSheet {
    val PERMISSION_Album = 101 // 앨범 권한 처리
    val REQUEST_GET_IMAGE = 105
    private var imageArray : ArrayList<Uri> = arrayListOf()
    private var imageEncodeArray : ArrayList<String> = arrayListOf()
    var TBID: Int? = 0
    val spinnerList = listOf("자유게시판", "장터게시판", "QnA게시판")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var communityType = arguments?.getInt("communityType")
        var QPID = arguments?.getInt("QPID")
        if (QPID == null){
            QPID = 0
        }
        if(communityType == null){
            communityType = 0
        }
        binding.communityRecyclerViewPostImages.visibility = GONE
        binding.communityRecyclerViewPostBook.visibility = GONE
        binding.communityEdittextPostContents.addTextChangedListener {
            binding.communityButtonWriteSubmit.setBackgroundColor(R.drawable.background_round_round_primary)
        }
        /*--------------------------------------------작성하기 버튼 호출부--------------------------------------------*/
        binding.communityButtonWriteSubmit.setOnClickListener {
            if(binding.communityEdittextPostTitle.text.length >0 && binding.communityEdittextPostContents.text.length > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    val bookkyService = ApplicationClass.getInstance().getRetrofit()
                    Log.d("imageisright?", imageEncodeArray[0])
                    postWrite(
                        bookkyService, communityType!!, WritePostBodyDataModel(
                            binding.communityEdittextPostTitle.text.toString(),
                            binding.communityEdittextPostContents.text.toString(),
                            TBID!!,
                            QPID!!,
                            imageEncodeArray
                        )
                    )
                }
            }
        }
        /*--------------------------------------------Gallery 호출부--------------------------------------------*/
        binding.communityEdittextAddImages.setOnClickListener {
            requirePermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_Album
            )
            binding.communityRecyclerViewPostImages.visibility = VISIBLE
        }
        /*--------------------------------------------Spinner 호출부--------------------------------------------*/
        val adapter = ArrayAdapter(requireContext(),R.layout.spinner_item,spinnerList )
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) communityType = position
                Log.d("communityType", communityType.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        /*--------------------------------------------BottomSheet 호출부--------------------------------------------*/
        binding.communityEdittextAddBook.setOnClickListener {
            bottomSheet = PostBookListBottomSheetDialog(requireContext(),this)
            bottomSheet.show()
            //BottomSheet 띄우는 부분
        }
    }

    override fun getData(data: WriteBookSearchDataModel) {
        binding.communityRecyclerViewPostBook.visibility = VISIBLE
        TBID = data.TBID
        writeBookAdapter(data)
    }
    /*--------------------------------------------RecyclerView Adapter--------------------------------------------*/
    private fun writeBookAdapter(Book: WriteBookSearchDataModel) {
        binding.communityRecyclerViewPostBook.adapter = WritePostBookAdapter(Book, binding)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecyclerViewPostBook.layoutManager = linearLayoutManager
    }
    private fun writeImagesAdapter(imageData: ArrayList<Uri>) {
        binding.communityRecyclerViewPostImages.adapter = WritePostImageAdapter(imageData, binding)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.communityRecyclerViewPostImages.layoutManager = linearLayoutManager
    }
    /*--------------------------------------------API호출 함수부--------------------------------------------*/
    private fun postWrite(bookkyService: BookkyService,communityType:Int, postData : WritePostBodyDataModel){
        bookkyService.writePost(communityType, postData)
            .enqueue(object : Callback<BaseResponse<String>> {
                override fun onFailure(
                    call: Call<BaseResponse<String>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<BaseResponse<String>>,
                    response: Response<BaseResponse<String>>
                ) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        findNavController().popBackStack()
                    }
                }
            })
    }


    /*--------------------------------------------갤러리 관련 함수부--------------------------------------------*/
    private fun getBase64ForUriAndPossiblyCrash(uri: Uri): String {
        var imageBase64 : String = ""
        try {
            val bytes = requireActivity().contentResolver.openInputStream(uri)!!.readBytes()

            imageBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
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
                permissions.all { PermissionChecker.checkSelfPermission(
                    requireContext(),
                    it
                ) == PermissionChecker.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
            }
        }
    }
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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_GET_IMAGE)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GET_IMAGE -> {
                    if (data?.clipData != null) {
                        data?.clipData?.let { uri ->
                            val size = data.clipData!!.itemCount
                            if (size > 5) {
                                showCustomToast("사진은 5장까지 첨부할 수 있습니다.")
                                return
                            }
                            for (i in 0 until size) {
                                val imageUri = data.clipData!!.getItemAt(i).uri
                                imageArray.add(imageUri)
                                imageEncodeArray.add("data:image/jpeg;base64,"+getBase64ForUriAndPossiblyCrash(imageUri))
                            }
                        }
                    } else {
                        data?.data?.let { uri ->
                            if(uri != null){
                                imageArray.add(uri)
                                val imageUri = "data:image/jpeg;base64,"+ getBase64ForUriAndPossiblyCrash(uri)
                                imageEncodeArray.add(imageUri)
                            }
                        }
                    }
                }
            }
            writeImagesAdapter(imageArray)
        }
    }

}
interface getDatafromBottomSheet{
    fun getData(data : WriteBookSearchDataModel)
}