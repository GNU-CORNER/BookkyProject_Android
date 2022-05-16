package com.example.bookkyandroid.ui.fragment.search

import android.os.Bundle
import android.view.View
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.SearchResultResponseDataModel
import com.example.bookkyandroid.databinding.FragmentSearchResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultFragment: BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = RetrofitManager.getInstance().bookkyService
        val keyword = arguments?.getString("keyword")
        searchBook(bookkyService, keyword.toString())
    }

    private fun searchBook(bookkyService: BookkyService, keyword:String){
        bookkyService.searchBook(keyword)
            .enqueue(object : Callback<BaseResponse<SearchResultResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<SearchResultResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<SearchResultResponseDataModel>>, response: Response<BaseResponse<SearchResultResponseDataModel>>){
                    if (response.isSuccessful.not()) {

                        return
                    }
                    response.body()?.let {

                    }
                }
            })
    }
}