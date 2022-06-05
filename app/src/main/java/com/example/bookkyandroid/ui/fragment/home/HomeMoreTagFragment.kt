package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.HomeBookDataModel
import com.example.bookkyandroid.data.model.HomeBookListDataModel
import com.example.bookkyandroid.data.model.HomeMoreTagResponseDataModel
import com.example.bookkyandroid.databinding.FragmentMoreTagBinding
import com.example.bookkyandroid.ui.adapter.HomeMoreTagBookListAdpater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMoreTagFragment : BaseFragment<FragmentMoreTagBinding>(FragmentMoreTagBinding::bind, R.layout.fragment_more_tag) {
    private var data : HomeMoreTagResponseDataModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoadingDialog(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            getHomeData(bookkyService)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(data != null){
            homeMoreTagBookListAdapterSet(data!!.bookList!!)
            successToGetTagData(data!!.nickname!!)
        }
    }
    private fun successToGetTagData(nickname: String){
        binding.moreTagTextViewNickname.setText(nickname)
        Thread.sleep(700)
        dismissLoadingDialog()
    }
    private fun homeMoreTagBookListAdapterSet(DataModels: ArrayList<HomeBookListDataModel?>){
        binding.recyclerViewMoreTagBookList.adapter = HomeMoreTagBookListAdpater(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMoreTagBookList.layoutManager = linearLayoutManager
    }
    private fun getHomeData(bookkyService: BookkyService){
        bookkyService.getHomeMoreTagData()
            .enqueue(object : Callback<BaseResponse<HomeMoreTagResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<HomeMoreTagResponseDataModel>>, t: Throwable) {
                }

                override fun onResponse(call: Call<BaseResponse<HomeMoreTagResponseDataModel>>, response: Response<BaseResponse<HomeMoreTagResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        homeMoreTagBookListAdapterSet(it.result.bookList!!)
                        successToGetTagData(it.result.nickname!!)
                        data = it.result
                    }
                }
            })

    }
}