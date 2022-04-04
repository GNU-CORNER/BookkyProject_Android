package com.example.bookkyandroid.ui.fragment.suggestion

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.databinding.FragmentSuggestionBinding


class SuggestionFragment : BaseFragment<FragmentSuggestionBinding>(FragmentSuggestionBinding::bind, R.layout.fragment_suggestion) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.suggestionLinearlayoutStartBookky1.setOnClickListener {
            val action =
            SuggestionFragmentDirections.actionSuggestionFragmentToBookRecommendFragment()
            findNavController().navigate(action)
        }


    }


}
