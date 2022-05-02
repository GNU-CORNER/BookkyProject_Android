package com.example.bookkyandroid.ui.fragment.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.BookDetailTestModel
import com.example.bookkyandroid.databinding.FragmentBookDetailBinding
import com.example.bookkyandroid.ui.adapter.BookDetailReviewAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(FragmentBookDetailBinding::bind, R.layout.fragment_book_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isExpanded1 = false
        var isExpanded2 = false

        // Set listener

        binding.bookDetailTextViewExpand1.setOnClickListener {
            if(isExpanded1){
                binding.bookDetailTextViewIntro.maxLines = 2
                isExpanded1 = false

            }
            else{
                binding.bookDetailTextViewIntro.maxLines = 1000
                isExpanded1 = true
            }
        }
        binding.bookDetailTextViewExpand2.setOnClickListener {
            if(isExpanded2){
                binding.bookDetailTextViewIndex.maxLines = 2
                isExpanded2 = false

            }
            else{
                binding.bookDetailTextViewIndex.maxLines = 1000
                isExpanded2 = true
            }
        }

        // Set test data
        val tags = arrayListOf<String>(
            "# FrontEnd",
            "# REACT",
            "# DevOps",
            "# BackEnd",
            "# FrontEnd"
        )

        val reviews  = arrayListOf<BookDetailTestModel>(
            BookDetailTestModel("이다혜","여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되..", "2022-02-14", 5.0f, 2),
            BookDetailTestModel("이다혜","여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되..", "2022-02-14", 5.0f, 2),
            BookDetailTestModel("이다혜","여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되여러분 이거보세요 ㄹㅇ 개쩜ㅋㅋㅋㅋㅋ짱이죠 ㄹㅇ ㅋㅋㅋ 신기하지않나요 이정도면 ㄹㅇ ㅋㅋㅋ 이게 ㄹㅇ 말이되", "2022-02-14", 5.0f, 2)
        )


        // Set adapters
        tagAdapterSet(tags)
        reviewAdapterSet(reviews)




    }

    private fun tagAdapterSet(tags: ArrayList<String>) {
        binding.bookDetailRecyclerViewTags.adapter = MyInfoInterestedAreaAdapter(tags)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.bookDetailRecyclerViewTags.layoutManager = linearLayoutManager
    }


    private fun reviewAdapterSet(review: ArrayList<BookDetailTestModel>) {
        binding.bookDetailRecyclerViewReviews.adapter = BookDetailReviewAdapter(review)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.bookDetailRecyclerViewReviews.layoutManager = linearLayoutManager
    }


}