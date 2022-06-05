package com.example.bookkyandroid.ui.fragment.survey

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.databinding.FragmentSurveyselectorBinding
import com.example.bookkyandroid.ui.adapter.HomeMoreTagDetailAdapter
import com.example.bookkyandroid.ui.adapter.SurveySelectorAdapter
import com.example.bookkyandroid.ui.fragment.home.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveySelectorFragment:BaseFragment<FragmentSurveyselectorBinding>(FragmentSurveyselectorBinding::bind, R.layout.fragment_surveyselector) {
    var tagData : ArrayList<Int> = arrayListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val level = arguments?.getInt("level")
        val bookkyService = ApplicationClass.getInstance().getRetrofit()
        getSurveyItem(bookkyService)
        binding.surveySelectorButtonNext.setOnClickListener {
            if (tagData.size != 0){
                CoroutineScope(Dispatchers.IO).launch {
                    tagData.add(level!!)
                    Log.d("tagData", tagData.toString())
                    updateTag(tagData, bookkyService)
                }
            }
            else{
                showCustomToast("태그는 최소 1개 이상 선택되어야 합니다.")
            }
        }
    }
    fun putTagData(i:Int){
        tagData.add(i)
        binding.surveySelectorButtonNext.setText("제출 ("+tagData.size+"개) 선택")
        binding.surveySelectorButtonNext.setBackgroundResource(R.drawable.background_round_primary)
        binding.surveySelectorButtonNext.setTextColor(Color.WHITE)
    }
    fun delTagData(i:Int){
        tagData.remove(i)
        if(tagData.size == 0){
            binding.surveySelectorButtonNext.setText("관심분야를 선택해주세요")
            binding.surveySelectorButtonNext.setBackgroundResource(R.drawable.background_round_gray)
            binding.surveySelectorButtonNext.setTextColor(Color.parseColor("#6C95FF"))
        }
        else{
            binding.surveySelectorButtonNext.setText("제출 ("+tagData.size+"개) 선택")
        }
    }
    private fun surveyItemAdaptre(DataModels: SurveySelectorResponseDataModel){
        binding.surveySelectorRecyclerViewVerticalSelector.adapter = SurveySelectorAdapter(DataModels, this)
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
    private fun successTagPut(){
        val bundle = bundleOf("type" to "key")
        findNavController().navigate(R.id.action_surveySelectorFragment_to_homeFragment, bundle)

    }
    private fun updateTag(tagArray:ArrayList<Int>, bookkyService: BookkyService){
        val body = UpdateTagBodyDataModel(tagArray)
        bookkyService.updateTag(body)
            .enqueue(object : Callback<BaseResponse<UpdateTagResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<UpdateTagResponseDataModel>>, t: Throwable) {
                }

                override fun onResponse(call: Call<BaseResponse<UpdateTagResponseDataModel>>, response: Response<BaseResponse<UpdateTagResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        successTagPut()
                    }
                }
            })
    }
}