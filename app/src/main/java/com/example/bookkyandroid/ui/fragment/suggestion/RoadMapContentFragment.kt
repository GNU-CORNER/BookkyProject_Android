package com.example.bookkyandroid.ui.fragment.suggestion

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.databinding.FragmentRoadMapContentBinding
import com.example.bookkyandroid.ui.adapter.RoadMapContentAdapter

class RoadMapContentFragment: BaseFragment<FragmentRoadMapContentBinding>(
    FragmentRoadMapContentBinding::bind, R.layout.fragment_road_map_content) {
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arrayListOf("HTTP가 뭔가요?", "브라우저는 어떻게 작동하나요?","인터넷은 무엇인가요?")
        navController = findNavController()
        roadMapContentAdapterSet(data, navController)


    }

    private fun roadMapContentAdapterSet(data: ArrayList<String>, navController: NavController) {
        binding.roadMapContentRecyclerView.adapter = RoadMapContentAdapter(data, navController)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.roadMapContentRecyclerView.layoutManager = linearLayoutManager
    }

}