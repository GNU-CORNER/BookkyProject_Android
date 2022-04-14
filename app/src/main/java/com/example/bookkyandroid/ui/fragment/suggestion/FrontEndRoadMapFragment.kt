package com.example.bookkyandroid.ui.fragment.suggestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
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

        //test data
        val interestedBooks = arrayListOf<String>(
            "Deep Learing",
            "혼자 공부하는 머신러닝 + 딥러닝",
            "개를 다루는 기술",
            "REACT 리액트"
        )

        myInterestedBooksAdapterSet(interestedBooks)

        setChangeTitle(0)

        val viewPager = binding.frontEndRoadMapViewPager
        val roadMapViewPagerAdapter = RoadMapViewPagerAdapter(this)
        viewPager.adapter = roadMapViewPagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                setChangeTitle(position)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })



        binding.frontEndRoadMapLinearLayout1.setOnClickListener {
            val current = viewPager.currentItem - 1
            viewPager.setCurrentItem(current, true)
        }

        binding.frontEndRoadMapLinearLayout2.setOnClickListener {
            val current = viewPager.currentItem + 1
            viewPager.setCurrentItem(current, true)
        }

    }


    private fun myInterestedBooksAdapterSet(titles: ArrayList<String>) {
        binding.frontEndRoadMapRecyclerView.adapter = RoadMapAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.frontEndRoadMapRecyclerView.layoutManager = linearLayoutManager
    }

    private fun setTitle(current: Int): String {
        return when (current) {
            0 -> "NULL"
            1 -> "WEB"
            2 -> "Internet "
            else -> ""
        }
    }

    private fun setChangeTitle(position: Int) {
        when (position) {
            0 -> {
                binding.frontEndRoadMapImageButton1.visibility = INVISIBLE
                binding.frontEndRoadMapTextView1.text = setTitle(3)
                binding.frontEndRoadMapTextView2.text = setTitle(0)
                binding.frontEndRoadMapTextView3.text = setTitle(1)
            }
            1 -> {
                binding.frontEndRoadMapImageButton1.visibility = VISIBLE
                binding.frontEndRoadMapImageButton2.visibility = VISIBLE
                binding.frontEndRoadMapTextView1.text = setTitle(0)
                binding.frontEndRoadMapTextView2.text = setTitle(1)
                binding.frontEndRoadMapTextView3.text = setTitle(2)
            }
            2 -> {
                binding.frontEndRoadMapImageButton2.visibility = INVISIBLE
                binding.frontEndRoadMapTextView1.text = setTitle(1)
                binding.frontEndRoadMapTextView2.text = setTitle(2)
                binding.frontEndRoadMapTextView3.text = setTitle(3)
            }
        }
    }
}





