package com.example.bookkyandroid.ui.fragment.suggestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.databinding.FragmentBookRecommendBinding
import com.example.bookkyandroid.databinding.FragmentFrontEndRoadMapBinding
import com.example.bookkyandroid.ui.adapter.BookRecommendAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedBooksAdapter
import com.example.bookkyandroid.ui.adapter.RoadMapAdapter
import com.example.bookkyandroid.ui.adapter.RoadMapViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FrontEndRoadMapFragment: BaseFragment<FragmentFrontEndRoadMapBinding>(
    FragmentFrontEndRoadMapBinding::bind, R.layout.fragment_front_end_road_map) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewPager = binding.frontEndRoadMapViewPager
        val roadMapViewPagerAdapter = RoadMapViewPagerAdapter(this)
        viewPager.adapter = roadMapViewPagerAdapter


        //탭 부분 수정할 예정
        binding.frontEndRoadMapLinearLayout1.setOnClickListener {
            var current =   viewPager.currentItem - 1
            viewPager.setCurrentItem(current, false)
            binding.frontEndRoadMapTextView1.text = setTitle(current-1)
            binding.frontEndRoadMapTextView2.text = setTitle(current)
            binding.frontEndRoadMapTextView3.text = setTitle(current+1)


        }
        binding.frontEndRoadMapLinearLayout2.setOnClickListener {
            var current =   viewPager.currentItem + 1
            viewPager.setCurrentItem(current, false)
            binding.frontEndRoadMapTextView1.text = setTitle(current-1)
            binding.frontEndRoadMapTextView2.text = setTitle(current)
            binding.frontEndRoadMapTextView3.text = setTitle(current+1)

        }

        //test data
        val interestedBooks = arrayListOf<String>(
            "Deep Learing",
            "혼자 공부하는 머신러닝 + 딥러닝",
            "개를 다루는 기술",
            "REACT 리액트"
        )

        myInterestedBooksAdapterSet(interestedBooks)
    }

    private fun myInterestedBooksAdapterSet(titles: ArrayList<String>) {
        binding.frontEndRoadMapRecyclerView.adapter = RoadMapAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.frontEndRoadMapRecyclerView.layoutManager = linearLayoutManager
    }

    private fun setTitle(current : Int) : String {
        return when(current) {
            0  -> "NULL"
            1 -> "WEB"
            2 -> "Internet "
            else -> ""
        }
    }
}





