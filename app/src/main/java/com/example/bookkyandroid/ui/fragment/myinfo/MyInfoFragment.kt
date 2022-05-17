package com.example.bookkyandroid.ui.fragment.myinfo

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.MyReview
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.databinding.FragmentMyInfoBinding
import com.example.bookkyandroid.ui.adapter.MyInfoWritingAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedBooksAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoReviewAdapter


class MyInfoFragment : BaseFragment<FragmentMyInfoBinding>(
    FragmentMyInfoBinding::bind, R.layout.fragment_my_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set test data
        val tags = arrayListOf<String>(
            "# FrontEnd",
            "# REACT",
            "# DevOps",
            "# BackEnd",
            "# FrontEnd"
        )

        val interestedBooks = arrayListOf<String>(
            "Deep Learing",
            "혼자 공부하는 머신러닝 + 딥러닝",
            "개를 다루는 기술",
            "REACT 리액트"
        )

        val myWriting = arrayListOf<MyWriting>(
            MyWriting("내 글 이다 내글 내글", "여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되..",2,2, 0),
            MyWriting("내 글 이다 내글 내글", "여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되..",2,2, 0)
        )

        val myReview  = arrayListOf<MyReview>(
            MyReview("REACT 리액트","여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되..", "사이토 고키 / 길벗", 5, 2),
            MyReview("REACT 리액트","여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되..", "사이토 고키 / 길벗", 5, 2)
        )

        //Set adapters
        myInterestedAraAdapterSet(tags)
        myInterestedBooksAdapterSet(interestedBooks)
        myWritingAdapterSet(myWriting)
        myReviewAdapterSet(myReview)


    }

    private fun myInterestedAraAdapterSet(tags: ArrayList<String>) {
        binding.myInfoRecyclerViewInterestedArea.adapter = MyInfoInterestedAreaAdapter(tags)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.myInfoRecyclerViewInterestedArea.layoutManager = linearLayoutManager
    }

    private fun myInterestedBooksAdapterSet(titles: ArrayList<String>) {
        binding.myInfoRecyclerViewInterestedBooks.adapter = MyInfoInterestedBooksAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.myInfoRecyclerViewInterestedBooks.layoutManager = linearLayoutManager
    }

    private fun myWritingAdapterSet(myWriting: ArrayList<MyWriting>) {
        binding.myInfoRecyclerViewMyWriting.adapter = MyInfoWritingAdapter(myWriting)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myInfoRecyclerViewMyWriting.layoutManager = linearLayoutManager
    }

    private fun myReviewAdapterSet(myReview: ArrayList<MyReview>) {
        binding.myInfoRecyclerViewMyReview.adapter = MyInfoReviewAdapter(myReview)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myInfoRecyclerViewMyReview.layoutManager = linearLayoutManager
    }
}
