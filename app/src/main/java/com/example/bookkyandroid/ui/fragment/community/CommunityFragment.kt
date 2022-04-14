package com.example.bookkyandroid.ui.fragment.community

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.databinding.FragmentCommunityBinding
import com.example.bookkyandroid.ui.adapter.CommunityPostAdapter


class CommunityFragment : BaseFragment<FragmentCommunityBinding>(
    FragmentCommunityBinding::bind, R.layout.fragment_community) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.communityButtonWrite.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_communityWriteFragment)
        }
        binding.communityImageButtonSearch.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_communitySearchPostFragment)
        }

        val communityPost = arrayListOf<MyWriting>(
            MyWriting("첫번째 글", "딩동댕 초인종 소리에 얼른 문을 열었더니 그토록 기다리던 아빠가 문 앞에 서 계셨죠",1,1),
            MyWriting("두번째 글", "너무나 반가워 웃으며 아빠하고 불렀는데 어쩐지 오늘 아빠의 얼굴이 우울해 보이네요",2,2),
            MyWriting("세번째 글", "무슨 일이 생겼나요 무슨 걱정 있나요 마음대로 안 되는 일, 오늘 있었나요?",3,3),
            MyWriting("네번째 글", "아빠 힘내세요 우리가 있잖아요 아빠 힘내세요 우리가 있어요 힘내세요 아빠",4,4),
            MyWriting("다섯번째 글", "깊고 작은 산골짜기 사이로 맑은 물 흐르는 작은 샘터에",5,5),
            MyWriting("여섯번째 글", "예쁜 꽃들 사이에 살짝 숨겨진 이슬 먹고 피어난",6,6),
            MyWriting("일곱번째 글", "네잎 클로버 랄랄라 한잎 랄랄라 두잎 랄랄라 세잎 랄랄라 네잎",7,7),
            MyWriting("여덟번째 글", "행운을 가져다 준다는 수줍은 얼굴의 미소",8,8),
            MyWriting("아홉번째 글", "한줄기의 따스한 햇살 받으며 희망으로 가득한 나의 친구야",9,9),
            MyWriting("열번째 글", "빛처럼 밝은 마음으로 너를 닮고 싶어",10,10),
        )

        postAdapterSet(communityPost, findNavController())
    }

    private fun postAdapterSet(myWriting: ArrayList<MyWriting>, NavControl : NavController) {

        binding.communityRecyclerViewPost.adapter = CommunityPostAdapter(myWriting, NavControl)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecyclerViewPost.layoutManager = linearLayoutManager
    }

}