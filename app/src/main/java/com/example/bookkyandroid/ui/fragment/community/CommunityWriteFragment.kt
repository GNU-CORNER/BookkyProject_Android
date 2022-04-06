package com.example.bookkyandroid.ui.fragment.community

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.databinding.FragmentCommunityPostWriteBinding


class CommunityWriteFragment : BaseFragment<FragmentCommunityPostWriteBinding>(
    FragmentCommunityPostWriteBinding::bind, R.layout.fragment_community_post_write) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.communityTextViewBack.setOnClickListener {
            findNavController().navigate(R.id.action_communityWriteFragment_to_communityFragment)
        }

    }

    private fun postAdapterSet(myWriting: ArrayList<MyWriting>) {



    }

}