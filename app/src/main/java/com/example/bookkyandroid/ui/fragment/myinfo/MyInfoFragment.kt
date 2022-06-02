package com.example.bookkyandroid.ui.fragment.myinfo

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.marginRight
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.databinding.FragmentMyInfoBinding
import com.example.bookkyandroid.ui.adapter.MyInfoWritingAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedBooksAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoReviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep


class MyInfoFragment : BaseFragment<FragmentMyInfoBinding>(
    FragmentMyInfoBinding::bind, R.layout.fragment_my_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        showLoadingDialog(requireContext())
        CoroutineScope(Dispatchers.IO).launch{
            val bookkyService = RetrofitManager.getInstance().bookkyService
            val access_token = ApplicationClass.getInstance().getDataStore().accessToken.first()

            getMyProfileData(bookkyService,access_token)
        }
        binding.myInfoTextViewReset.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_surveyFragment)
        }
        binding.myInfoTextViewMore1.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_myFavoriteBookFragment)
        }
        binding.myInfoTextViewMore2.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_myProfileCommunityFragment)
        }
        binding.myInfoTextViewMore3.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_myReviewFragment)
        }


    }
    private fun successToCall(image : String, nickname:String) {
        Log.d("image", image)
        Glide.with(this)
            .load(image)
            .override(80, 80)
            .diskCacheStrategy(DiskCacheStrategy.NONE )
            .skipMemoryCache(true)
            .into(binding.myInfoCircleImageProfile);
        binding.myInfoImgBtnArrow.setOnClickListener {
            val bundle = bundleOf("image22" to image)
            bundle.putString("nickname", nickname)
            findNavController().navigate(
                R.id.action_myInfoFragment_to_modifyProfileFragment,
                bundle
            )
        }
    }
    private fun myInterestedAraAdapterSet(tags: ArrayList<TagDataResponseDataModel>) {
        binding.myInfoRecyclerViewInterestedArea.adapter = MyInfoInterestedAreaAdapter(tags)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.myInfoRecyclerViewInterestedArea.layoutManager = linearLayoutManager
    }

    private fun myInterestedBooksAdapterSet(bookData: ArrayList<HomeBookDataModel>) {
        binding.myInfoRecyclerViewInterestedBooks.adapter = MyInfoInterestedBooksAdapter(bookData)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.myInfoRecyclerViewInterestedBooks.layoutManager = linearLayoutManager
    }
    private fun myInterestedBooksNoObjectAdapterSet(bookData: ArrayList<HomeBookDataModel>) {
        binding.myInfoRecyclerViewInterestedBooks.adapter = MyInfoInterestedBooksAdapter(bookData)
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(20 * displayMetrics.density)
        var layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        binding.myInfoRecyclerViewInterestedBooks.layoutParams = layoutParams
        layoutParams.setMargins(dp,0,dp,dp)
        val linearLayoutManager = LinearLayoutManager(activity)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myInfoRecyclerViewInterestedBooks.layoutManager = linearLayoutManager
    }

    private fun myWritingAdapterSet(postData: ArrayList<MyProfilePostDataModel>) {
        var postProcessData: ArrayList<MyProfilePostDataModel> = arrayListOf()
        if (postData.size > 1) {
            postProcessData.add(postData[0])
            postProcessData.add(postData[1])
        }
        else{
            postProcessData = postData
        }
        binding.myInfoRecyclerViewMyWriting.adapter = MyInfoWritingAdapter(postProcessData)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myInfoRecyclerViewMyWriting.layoutManager = linearLayoutManager
    }

    private fun myReviewAdapterSet(reviewData: ArrayList<MyProfileReviewDataModel>) {
        var reviewProcessData : ArrayList<MyProfileReviewDataModel> = arrayListOf()
        if (reviewData.size > 1){
            reviewProcessData.add(reviewData[0])
            reviewProcessData.add(reviewData[1])
        }
        else{
            reviewProcessData = reviewData
        }
        binding.myInfoRecyclerViewMyReview.adapter = MyInfoReviewAdapter(reviewProcessData)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myInfoRecyclerViewMyReview.layoutManager = linearLayoutManager
        binding.myInfoRecyclerViewMyReview.stopScroll()
    }

    private fun getMyProfileData(bookkyService:BookkyService, access_token:String){
        bookkyService.getMyprofile(access_token)
            .enqueue(object : Callback<BaseResponse<MyProfileResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<MyProfileResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<MyProfileResponseDataModel>>, response: Response<BaseResponse<MyProfileResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        binding.myInfoTextViewName.text = it.result.userData.nickname!!
                        Log.d("tesxt",it.result.userData.userThumbnail.toString())
                        successToCall(it.result.userData.userThumbnail.toString(), it.result.userData.nickname.toString())
                        //이미지 뷰 처리는 Glide 라이브러리 사용 예정

                        if(it.result.favoriteBookList.size == 0){
                            myInterestedBooksNoObjectAdapterSet(arrayListOf(HomeBookDataModel(0, "","", "")))
                        }
                        else{
                            myInterestedBooksAdapterSet(it.result.favoriteBookList)
                        }
                        if(it.result.userPostList.size == 0){
                            myWritingAdapterSet(arrayListOf(MyProfilePostDataModel(0,"","",0,0,0)))
                        }
                        else{
                            myWritingAdapterSet(it.result.userPostList)
                        }
                        if(it.result.userReviewList.size == 0){
                            myReviewAdapterSet(arrayListOf(MyProfileReviewDataModel(0,0,0,"",0,"",0.0F,0,false,false,"","","","")))
                        }
                        else{
                            myReviewAdapterSet(it.result.userReviewList)
                        }
                        myInterestedAraAdapterSet(it.result.userData.userTagList!!)

                        sleep(1000)
                        dismissLoadingDialog()
                    }
                }
            })
    }
}
