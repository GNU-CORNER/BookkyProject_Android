package com.example.bookkyandroid.ui.fragment.suggestion

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.SuggestionContents
import com.example.bookkyandroid.databinding.FragmentSuggestionBinding
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter
import com.example.bookkyandroid.ui.adapter.SuggestionAdapter


class SuggestionFragment : BaseFragment<FragmentSuggestionBinding>(FragmentSuggestionBinding::bind, R.layout.fragment_suggestion) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set test data

        val contents = arrayListOf(
            SuggestionContents(
                "멍탐점 북키",
                "내가 책을 추천하지",
                "",
                "북키와 대화하기",
                R.drawable.suggestion_item_background_img,
                isNew = false,
                isLast = false
            ),
            SuggestionContents(
                "안내견 북키 - FrontEnd -",
                "요즘 유행하는 웹 개발, 나도 해볼까?",
                "북키가 안내해줄게 !",
                "북키에게 안내받기",
                R.drawable.suggestion_item_background_img2,
                isNew = false,
                isLast = false
            ),
            SuggestionContents(
                "안내견 북키 - BackEnd -",
                "진짜 프로그래머는 백엔드를 한다 ! 나도 해볼까?",
                "북키가 안내해줄게 !",
                "북키에게 안내받기",
                R.drawable.suggestion_item_background_img2,
                isNew = true,
                isLast = false
            ),
            SuggestionContents(
                "북키가 새로운 아이디어를\n생각중이에요.",
                "추후 추가될 기능을 기대해주세요",
                "",
                "",
                R.drawable.suggestion_item_background_img3,
                isNew = false,
                isLast = true
            )
        )

        //Set adapter
       // suggestionAdapterSet(contents)



    }
/*
    private fun suggestionAdapterSet(contents: ArrayList<SuggestionContents>) {
        binding.suggestionRecyclerView.adapter = SuggestionAdapter(contents)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.suggestionRecyclerView.layoutManager = linearLayoutManager
    }
*/

}
