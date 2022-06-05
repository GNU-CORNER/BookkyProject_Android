package com.example.bookkyandroid.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.SearchResultDataModel
import com.example.bookkyandroid.data.model.SearchResultResponseDataModel
import com.example.bookkyandroid.data.model.TagDataResponseDataModel
import com.example.bookkyandroid.databinding.FragmentSearchResultBinding
import com.example.bookkyandroid.ui.adapter.SearchNoResultListAdapter
import com.example.bookkyandroid.ui.adapter.SearchResultListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultFragment: BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result) {
    private var page = 1
    private var totalPage = 1
    private var flag = false
    var keyword : String? = null
    var data : SearchResultResponseDataModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keyword = arguments?.getString("keyword")
        val bookkyService = ApplicationClass.getInstance().getRetrofit()
        binding.textViewResultTitle.setText(keyword)
        if (data != null){
            binding.textViewResultTitle.setText(keyword)
            recyclerviewBinder(data!!.searchData)
            binding.searchEditTextSearchInput.setText(keyword)
            page = 1
        }
        binding.searchEditTextSearchInput.setText(keyword)
        searchBook(bookkyService, keyword.toString())
        binding.imageButton4.setOnClickListener{
            searchBook(bookkyService, binding.searchEditTextSearchInput.text.toString())
            page = 1
        }
        binding.recyclerViewSearchResultKeywordList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                // 스크롤이 끝에 도달했는지 확인
                if (!binding.recyclerViewSearchResultKeywordList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
//                    SearchResultListAdapter.deleteLoading()
                    if(totalPage == 1){
                        flag = true
                    }
                    if(!flag) {
                        searchBook(bookkyService, keyword.toString())
                    }
                }
            }
        })
    }
    private fun recyclerviewBinder(searchData : ArrayList<SearchResultDataModel>) {
        if (searchData.size > 0){
            searchData.add(
                SearchResultDataModel(
                    0,
                    " ",
                    " ",
                    " ",
                    0.0F,
                    " ",
                    " ",
                    " ",
                    arrayListOf(TagDataResponseDataModel(" ", 0,))
                )
            )
            binding.recyclerViewSearchResultKeywordList.adapter = SearchResultListAdapter(searchData)
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerViewSearchResultKeywordList.layoutManager = linearLayoutManager
            binding.recyclerViewSearchResultKeywordList.adapter!!.notifyItemRangeInserted(
                (page - 1) * 25,
                25
            )
        }
        else{
            binding.recyclerViewSearchResultKeywordList.adapter = SearchNoResultListAdapter("searchData")
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerViewSearchResultKeywordList.layoutManager = linearLayoutManager
        }
    }

    private fun searchBook(bookkyService: BookkyService, keyword:String) {
        bookkyService.searchBook(keyword, 25, page)
            .enqueue(object : Callback<BaseResponse<SearchResultResponseDataModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<SearchResultResponseDataModel>>,
                    t: Throwable
                ) {
                    flag = true
                    recyclerviewBinder(arrayListOf())

                    Log.d("searchSuccess", t.toString())
                    Log.d("searchSuccess","검색 결과가 없습니다.")
                }

                //Todo 무한 스크롤 구현해야함
                override fun onResponse(
                    call: Call<BaseResponse<SearchResultResponseDataModel>>,
                    response: Response<BaseResponse<SearchResultResponseDataModel>>
                ) {
                    if (response.code()==204) {
                        Log.d("searchSuccess","검색 결과가 없습니다.")
                        return
                    }
                    response.body()?.let {
                        binding.progressBar2.visibility = View.GONE
                        recyclerviewBinder(it.result.searchData)
                        totalPage = it.result.total
                        page++
                        data = it.result
                    }
                }
            })
    }
}