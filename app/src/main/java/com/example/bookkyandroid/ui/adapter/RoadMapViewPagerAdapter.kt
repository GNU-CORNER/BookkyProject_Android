package com.example.bookkyandroid.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookkyandroid.ui.fragment.suggestion.RoadMapContentFragment

class RoadMapViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)

        return RoadMapContentFragment()
    }
}