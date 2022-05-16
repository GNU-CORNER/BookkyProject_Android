package com.example.bookkyandroid.ui.fragment.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.MyReviewResponseDataModel
import com.example.bookkyandroid.data.model.SearchResultResponseDataModel
import com.example.bookkyandroid.data.model.TagDataResponseDataModel
import com.example.bookkyandroid.databinding.FragmentSearchBinding
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter
import com.example.bookkyandroid.ui.adapter.RecentKeywordListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = RetrofitManager.getInstance().bookkyService
        val testTagList = arrayListOf<TagDataResponseDataModel>(TagDataResponseDataModel("#REACT",1),TagDataResponseDataModel("#JavaScript",1),TagDataResponseDataModel("#FrontEnd",1),TagDataResponseDataModel("#파이썬asdasdasdasdasd",1),TagDataResponseDataModel("#파이썬",1),TagDataResponseDataModel("#Cloud",1),TagDataResponseDataModel("#DevOps",1),TagDataResponseDataModel("#BackEnd",1))
        val keywords = arrayListOf<String>("마법소녀", "모두다 거짓말이야", "개발하기 싫다", "그만...개발..", "체전 하고싶었는데.")
        searchTagsAdapter(testTagList)
        recentKeywordAdapter(keywords)
    }
    private fun searchTagsAdapter(tags: ArrayList<TagDataResponseDataModel>) {
        binding.recyclerViewSearchTagList.adapter = MyInfoInterestedAreaAdapter(tags)
        val linearLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL)
        
        binding.recyclerViewSearchTagList.layoutManager = linearLayoutManager


    }
    private fun recentKeywordAdapter(keyword : ArrayList<String>){
        binding.recyclerViewRecentKeywordList.adapter = RecentKeywordListAdapter(keyword)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewRecentKeywordList.layoutManager = linearLayoutManager
    }

}
