package com.example.bookkyandroid.ui.fragment.signin

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.databinding.FragmentFindpwBinding

class FindPwFragment : BaseFragment<FragmentFindpwBinding>(FragmentFindpwBinding::bind, R.layout.fragment_findpw) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.findpwButtonNext.setOnClickListener{
            findNavController().navigate(R.id.action_findPwFragment_to_findPwNewPwFragment)
        }
    }
}