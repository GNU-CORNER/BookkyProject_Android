package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.HomeCommunityDataModel
import com.example.bookkyandroid.data.model.HomeTagBookDataModel
import com.example.bookkyandroid.databinding.FragmentHomeBinding
import com.example.bookkyandroid.ui.adapter.HomeCommunityShortAdapter
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var textNickname: TextView = binding.textViewHomeNickName
        textNickname.text = "nickname"
        val bookList = arrayListOf<String>("test1", "test2", "test3")
        val communityDummyData = arrayListOf<HomeCommunityDataModel>(
            HomeCommunityDataModel("핫게시판", "한 번 읽어보고 마스터한 책 사실 분~!!!"),
            HomeCommunityDataModel("QnA게시판", "함수를 썼는데 이상해요..."),
            HomeCommunityDataModel("자유게시판", "카카오 공채 떴던데 보신 분 있으신가요?")
        )
        val tagBookDummyData = arrayListOf<HomeTagBookDataModel>(
            HomeTagBookDataModel("test1", "")
        )
        myTagBooksAdapterSet1(bookList)
        myTagBooksAdapterSet2(bookList)
        homeCommunitySet(communityDummyData)
    }

    private fun myTagBooksAdapterSet1(titles: ArrayList<String>) {
        binding.recyclerViewHomeBookList1.adapter = HomeTagByBooksAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList1.layoutManager = linearLayoutManager
    }
    private fun myTagBooksAdapterSet2(titles: ArrayList<String>) {
        binding.recyclerViewHomeBookList2.adapter = HomeTagByBooksAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList2.layoutManager = linearLayoutManager
    }
    private fun homeCommunitySet(titles: ArrayList<HomeCommunityDataModel>) {
        binding.recyclerViewHomeCommunityList.adapter = HomeCommunityShortAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHomeCommunityList.layoutManager = linearLayoutManager
    }
}
