package com.example.bookkyandroid.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.SearchResultResponseDataModel
import com.example.bookkyandroid.databinding.FragmentSearchResultBinding
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter
import com.example.bookkyandroid.ui.adapter.SearchResultListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultFragment: BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = RetrofitManager.getInstance().bookkyService
        val keyword = arguments?.getString("keyword")
        binding.searchEditTextSearchInput.setText(keyword)
        searchBook(bookkyService, keyword.toString())
        binding.imageButton4.setOnClickListener{
            searchBook(bookkyService, binding.searchEditTextSearchInput.text.toString())
        }
    }
    private fun successToGetSearchData(keyword : String){
        binding.textViewResultTitle.setText(keyword)
    }
    private fun recyclerviewBinder(searchData : ArrayList<SearchResultResponseDataModel>){
        binding.recyclerViewSearchResultKeywordList.adapter = SearchResultListAdapter(searchData)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewSearchResultKeywordList.layoutManager = linearLayoutManager
    }
    private fun searchBook(bookkyService: BookkyService, keyword:String){
        bookkyService.searchBook(keyword,25,1)
            .enqueue(object : Callback<BaseResponse<ArrayList<SearchResultResponseDataModel>>> {
                override fun onFailure(call: Call<BaseResponse<ArrayList<SearchResultResponseDataModel>>>, t: Throwable) {
                    Log.d("searchSuccess", t.toString())
                }
                //Todo 무한 스크롤 구현해야함
                override fun onResponse(call: Call<BaseResponse<ArrayList<SearchResultResponseDataModel>>>, response: Response<BaseResponse<ArrayList<SearchResultResponseDataModel>>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        successToGetSearchData(keyword)
                        Log.d("searchSuccess", it.result.toString())
                        recyclerviewBinder(it.result)
                    }
                }
            })
    }
}