package com.example.bookkyandroid.ui.fragment.suggestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.Message
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.databinding.FragmentBookRecommendBinding
import com.example.bookkyandroid.databinding.FragmentSuggestionBinding
import com.example.bookkyandroid.ui.adapter.BookRecommendAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter
import com.example.bookkyandroid.util.Constants.RECEIVE_ID
import com.example.bookkyandroid.util.Constants.SEND_ID


class BookRecommendFragment  : BaseFragment<FragmentBookRecommendBinding>(FragmentBookRecommendBinding::bind, R.layout.fragment_book_recommend) {

    lateinit var adapter : BookRecommendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val message = arrayListOf<Message>(
            Message("내이름은 북키 ! 탐정이죠.",RECEIVE_ID, true),
            Message("너에게 맞는 책을 추천해줄게 !",RECEIVE_ID, false),
            Message("자바스크립트과 관련된 책이야?",RECEIVE_ID, false)
        )

        bookRecommendAraAdapterSet(message)

        binding.bookRecommendButton1.setOnClickListener {
            adapter.insertMessage(Message("맞아 !", SEND_ID, false))
            binding.bookRecommendRecyclerview.scrollToPosition(adapter.itemCount - 1)
        }
        binding.bookRecommendButton2.setOnClickListener {
            adapter.insertMessage(Message("음..아니야", SEND_ID, false))
            binding.bookRecommendRecyclerview.scrollToPosition(adapter.itemCount - 1)
        }
        binding.bookRecommendButton3.setOnClickListener {
            adapter.insertMessage(Message("잘 모르겠어.", SEND_ID, false))
            binding.bookRecommendRecyclerview.scrollToPosition(adapter.itemCount - 1)
        }
        binding.bookRecommendButton4.setOnClickListener {
            adapter.insertMessage(Message("그런 편인거 같아", SEND_ID, false))
            binding.bookRecommendRecyclerview.scrollToPosition(adapter.itemCount - 1)
        }
        binding.bookRecommendButton5.setOnClickListener {
            adapter.insertMessage(Message("아닌 것 같은데?", SEND_ID, false))
            binding.bookRecommendRecyclerview.scrollToPosition(adapter.itemCount - 1)
        }
        binding.bookRecommendButton6.setOnClickListener {
            adapter.insertMessage(Message("결과를 보여줘 !", SEND_ID, false))
            binding.bookRecommendRecyclerview.scrollToPosition(adapter.itemCount - 1)
        }



    }

    private fun bookRecommendAraAdapterSet(message: ArrayList<Message>) {
        adapter = BookRecommendAdapter(message)
        binding.bookRecommendRecyclerview.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.bookRecommendRecyclerview.layoutManager = linearLayoutManager
    }


}
