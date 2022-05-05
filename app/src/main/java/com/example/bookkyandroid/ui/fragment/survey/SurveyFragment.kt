package com.example.bookkyandroid.ui.fragment.survey

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bookkyandroid.R
import com.example.bookkyandroid.databinding.FragmentSurveyBinding

class SurveyFragment:DialogFragment() {
    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSurveyBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        binding.surveyTextViewNext.setOnClickListener {

        }
        binding.surveyTextViewSelector1.setOnClickListener {
            binding.surveyTextViewSelector1.setBackgroundColor(Color.parseColor(R.color.colorPrimary.toString()))
            binding.surveyTextViewSelector2.setBackgroundColor(Color.parseColor(R.color.dark_onSurface.toString()))
        }
        binding.surveyTextViewSelector2.setOnClickListener {
            binding.surveyTextViewSelector2.setBackgroundColor(Color.parseColor(R.color.colorPrimary.toString()))
            binding.surveyTextViewSelector1.setBackgroundColor(Color.parseColor(R.color.dark_onSurface.toString()))
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}