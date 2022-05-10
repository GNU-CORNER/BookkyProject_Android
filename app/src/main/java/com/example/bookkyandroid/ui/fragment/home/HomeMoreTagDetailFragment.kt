package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.HomeBookListDataModel
import com.example.bookkyandroid.databinding.FragmentMoreTagDetailBinding
import com.example.bookkyandroid.ui.adapter.HomeMoreTagDetailVerticalAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMoreTagDetailFragment : BaseFragment<FragmentMoreTagDetailBinding>(FragmentMoreTagDetailBinding::bind, R.layout.fragment_more_tag_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tagID = arguments?.getInt("TID")
        val bookkyService = RetrofitManager.getInstance().bookkyService
        getHomeMoreTagDetailData(bookkyService, tagID!!)
    }
    private fun moreTagDetailAdapter(headline : String, DataModels: HomeBookListDataModel){
        binding.textViewHomeMoreTagDetailHeadLine.text = headline
        binding.recyclerViewMoreTagDetailBookList.adapter = HomeMoreTagDetailVerticalAdapter(DataModels!!.data)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMoreTagDetailBookList.layoutManager = linearLayoutManager
    }

    private fun getHomeMoreTagDetailData(bookkyService: BookkyService, TID:Int){
        bookkyService.getHomeMoreTagDetailData(25,1,TID)
            .enqueue(object : Callback<BaseResponse<HomeBookListDataModel>> {
                override fun onFailure(call: Call<BaseResponse<HomeBookListDataModel>>, t: Throwable) {
                    Log.d("getHomeMoreTagDetail", t.toString())
                }

                override fun onResponse(call: Call<BaseResponse<HomeBookListDataModel>>, response: Response<BaseResponse<HomeBookListDataModel>>?){
                    if (response!!.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        moreTagDetailAdapter(it!!.result.tag, it.result)

                    }
                }
            })
    }
}
