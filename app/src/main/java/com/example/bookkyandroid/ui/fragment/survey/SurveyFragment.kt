package com.example.bookkyandroid.ui.fragment.survey

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.databinding.FragmentSurveyBinding

class SurveyFragment: BaseFragment<FragmentSurveyBinding>(FragmentSurveyBinding::bind, R.layout.fragment_survey) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = RetrofitManager.getInstance().bookkyService
        var touched1 = false
        var touched2 = false
        binding.surveyTextViewSelector1.setOnClickListener {
            if (touched2 == false) {
                if (touched1 == false) {
                    binding.surveyTextViewSelector1.setBackgroundResource(R.drawable.background_round_primary)
                    binding.surveyTextViewSelector1.setTextColor(Color.WHITE)
                    touched1 = true
                    binding.buttonSurveyNext.setBackgroundResource(R.drawable.background_round_primary)
                    binding.buttonSurveyNext.setTextColor(Color.WHITE)
                } else {
                    binding.surveyTextViewSelector1.setBackgroundResource(R.drawable.background_round_gray)
                    binding.surveyTextViewSelector1.setTextColor(Color.parseColor("#6C95FF"))
                    touched1 = false
                    binding.buttonSurveyNext.setBackgroundResource(R.drawable.background_round_gray)
                    binding.buttonSurveyNext.setTextColor(Color.parseColor("#6C95FF"))
                }
            }

        }
        binding.surveyTextViewSelector2.setOnClickListener {
            if (touched1 == false) {
                if (touched2 == false) {
                    binding.surveyTextViewSelector2.setBackgroundResource(R.drawable.background_round_primary)
                    binding.surveyTextViewSelector2.setTextColor(Color.WHITE)
                    touched2 = true
                    binding.buttonSurveyNext.setBackgroundResource(R.drawable.background_round_primary)
                    binding.buttonSurveyNext.setTextColor(Color.WHITE)
                } else {
                    binding.surveyTextViewSelector2.setBackgroundResource(R.drawable.background_round_gray)
                    binding.surveyTextViewSelector2.setTextColor(Color.parseColor("#6C95FF"))
                    touched2 = false
                    binding.buttonSurveyNext.setBackgroundResource(R.drawable.background_round_gray)
                    binding.buttonSurveyNext.setTextColor(Color.parseColor("#6C95FF"))
                }
            }
        }
        binding.buttonSurveyNext.setOnClickListener {
            if(touched1 == true || touched2 == true){
                findNavController().navigate(R.id.action_surveyFragment_to_surveySelectorFragment)
            }
        }
    }
}