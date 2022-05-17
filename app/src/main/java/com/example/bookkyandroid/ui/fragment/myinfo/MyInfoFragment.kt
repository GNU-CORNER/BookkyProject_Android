package com.example.bookkyandroid.ui.fragment.myinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.config.TokenManager
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentMyInfoBinding
import com.example.bookkyandroid.ui.adapter.MyInfoWritingAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedBooksAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoReviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyInfoFragment : BaseFragment<FragmentMyInfoBinding>(
    FragmentMyInfoBinding::bind, R.layout.fragment_my_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch{
            val bookkyService = RetrofitManager.getInstance().bookkyService
            RetrofitManager.getInstance().getToken()
            getMyProfileData(bookkyService)
        }

        binding.myInfoTextViewReset.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_surveyFragment)
        }
        binding.myInfoTextViewMore1.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_myFavoriteBookFragment)
        }
        binding.myInfoTextViewMore3.setOnClickListener {
            findNavController().navigate(R.id.action_myInfoFragment_to_myReviewFragment)
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

    private fun myWritingAdapterSet(postData: ArrayList<MyProfilePostDataModel>) {
        binding.myInfoRecyclerViewMyWriting.adapter = MyInfoWritingAdapter(postData)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myInfoRecyclerViewMyWriting.layoutManager = linearLayoutManager
    }

    private fun myReviewAdapterSet(reviewData: ArrayList<MyProfileReviewDataModel>) {
        binding.myInfoRecyclerViewMyReview.adapter = MyInfoReviewAdapter(reviewData)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myInfoRecyclerViewMyReview.layoutManager = linearLayoutManager
    }

    private fun getMyProfileData(bookkyService:BookkyService){
        val retrofitManagerInstance = RetrofitManager.getInstance()
        bookkyService.getMyprofile(retrofitManagerInstance.accessToken)
            .enqueue(object : Callback<BaseResponse<MyProfileResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<MyProfileResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<MyProfileResponseDataModel>>, response: Response<BaseResponse<MyProfileResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        TokenManager().refresh_token(bookkyService,retrofitManagerInstance.accessToken, retrofitManagerInstance.refreshToken)
                        return
                    }
                    response.body()?.let {
                        binding.myInfoTextViewName.text = it.result.userData.nickname!!
                        binding.myInfoTextViewName2.text = it.result.userData.nickname!!
                        binding.myInfoTextViewName3.text = it.result.userData.nickname!!
                        binding.myInfoTextViewName4.text = it.result.userData.nickname!!
                        binding.myInfoTextViewName5.text = it.result.userData.nickname!!
                        myInterestedAraAdapterSet(it.result.userData.userTagList!!)
                        myInterestedBooksAdapterSet(it.result.favoriteBookList)
                        myWritingAdapterSet(it.result.userPostList)
                        myReviewAdapterSet(it.result.userReviewList)
                    }
                }
            })
    }
}
