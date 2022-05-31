package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.HomeBookListDataModel
import com.example.bookkyandroid.data.model.HomeMoreTagDetailResponseDataModel
import com.example.bookkyandroid.databinding.FragmentMoreTagDetailBinding
import com.example.bookkyandroid.ui.adapter.HomeMoreTagDetailAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMoreTagDetailFragment : BaseFragment<FragmentMoreTagDetailBinding>(FragmentMoreTagDetailBinding::bind, R.layout.fragment_more_tag_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tagID = arguments?.getInt("TID")
        Log.d("home", tagID.toString())
        val bookkyService = RetrofitManager.getInstance().bookkyService
        getHomeMoreTagDetailData(bookkyService, tagID!!)
    }
    private fun moreTagDetailAdapter(DataModels: HomeBookListDataModel){
        binding.textViewHomeMoreTagDetailHeadLine.setText(DataModels.tag)
        binding.recyclerViewMoreTagDetailBookList.adapter = HomeMoreTagDetailAdapter(DataModels)
        val linearLayoutManager = GridLayoutManager(activity,4)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMoreTagDetailBookList.layoutManager = linearLayoutManager
    }

    private fun getHomeMoreTagDetailData(bookkyService: BookkyService, TID:Int){
        bookkyService.getHomeMoreTagDetailData(TID,25,1)
            .enqueue(object : Callback<BaseResponse<HomeMoreTagDetailResponseDataModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<HomeMoreTagDetailResponseDataModel>>,
                    t: Throwable
                ) {

                }
                override fun onResponse(
                    call: Call<BaseResponse<HomeMoreTagDetailResponseDataModel>>,
                    response: Response<BaseResponse<HomeMoreTagDetailResponseDataModel>>
                ) {
                    if(response.isSuccessful()){
                        response.body()?.let {
                            moreTagDetailAdapter(it.result.bookList)
                        }
                    }
                }


            })
    }
}
