package com.example.bookkyandroid.ui.fragment.myinfo

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.MyProfileReviewDataModel
import com.example.bookkyandroid.data.model.MyReviewResponseDataModel
import com.example.bookkyandroid.databinding.FragmentMyReviewBinding
import com.example.bookkyandroid.ui.adapter.MyInfoReviewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReviewFragment: BaseFragment<FragmentMyReviewBinding>(
    FragmentMyReviewBinding::bind, R.layout.fragment_my_review) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            getReviewData(bookkyService)
        }

    }
    private fun myReviewAdapterSet(reviewData: ArrayList<MyProfileReviewDataModel>) {
        binding.recyclerViewMyReview.adapter = MyInfoReviewAdapter(reviewData)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMyReview.layoutManager = linearLayoutManager
    }
    private fun getReviewData(bookkyService: BookkyService){
        bookkyService.getMyReview()
            .enqueue(object : Callback<BaseResponse<MyReviewResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<MyReviewResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<MyReviewResponseDataModel>>, response: Response<BaseResponse<MyReviewResponseDataModel>>){
                    if (response.isSuccessful.not()) {

                        return
                    }
                    response.body()?.let {
                        myReviewAdapterSet(it.result.reviewList)
                    }
                }
            })
    }
}