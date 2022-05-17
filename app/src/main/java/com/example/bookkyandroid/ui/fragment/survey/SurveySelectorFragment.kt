package com.example.bookkyandroid.ui.fragment.survey

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentSurveyselectorBinding
import com.example.bookkyandroid.ui.adapter.HomeMoreTagDetailAdapter
import com.example.bookkyandroid.ui.adapter.SurveySelectorAdapter
import com.example.bookkyandroid.ui.fragment.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveySelectorFragment:BaseFragment<FragmentSurveyselectorBinding>(FragmentSurveyselectorBinding::bind, R.layout.fragment_surveyselector) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = RetrofitManager.getInstance().bookkyService
        getSurveyItem(bookkyService)
    }
    private fun surveyItemAdaptre(DataModels: SurveySelectorResponseDataModel){
        binding.surveySelectorRecyclerViewVerticalSelector.adapter = SurveySelectorAdapter(DataModels)
        val linearLayoutManager = GridLayoutManager(activity,4)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.surveySelectorRecyclerViewVerticalSelector.layoutManager = linearLayoutManager
    }
    private fun getSurveyItem(bookkyService: BookkyService){
        bookkyService.getTagItem()
            .enqueue(object : Callback<BaseResponse<SurveySelectorResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<SurveySelectorResponseDataModel>>, t: Throwable) {
                }

                override fun onResponse(call: Call<BaseResponse<SurveySelectorResponseDataModel>>, response: Response<BaseResponse<SurveySelectorResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        surveyItemAdaptre(it.result)
                    }
                }
            })
    }
}