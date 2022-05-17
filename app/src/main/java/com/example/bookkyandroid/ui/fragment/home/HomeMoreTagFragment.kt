package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var accessToken : String = ""
        CoroutineScope(Dispatchers.IO).launch {
            accessToken = ApplicationClass.getInstance().getDataStore().accessToken.first()
        }
        val bookkyService = RetrofitManager.getInstance().bookkyService
        getHomeData(bookkyService ,accessToken)

    }
    private fun homeMoreTagBookListAdapterSet(DataModels: ArrayList<HomeBookListDataModel?>){
        binding.recyclerViewMoreTagBookList.adapter = HomeMoreTagBookListAdpater(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMoreTagBookList.layoutManager = linearLayoutManager
    }
    private fun getHomeData(bookkyService: BookkyService, access_token : String){
        bookkyService.getHomeMoreTagData(access_token)
            .enqueue(object : Callback<BaseResponse<HomeMoreTagResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<HomeMoreTagResponseDataModel>>, t: Throwable) {
                }

                override fun onResponse(call: Call<BaseResponse<HomeMoreTagResponseDataModel>>, response: Response<BaseResponse<HomeMoreTagResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        homeMoreTagBookListAdapterSet(it.result!!.bookList!!)
                    }
                }
            })
    }
}