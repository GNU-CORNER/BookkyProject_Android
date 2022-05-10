package com.example.bookkyandroid.ui.activity.survey

import android.os.Bundle
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseActivity
import com.example.bookkyandroid.databinding.ActivitySurveyBinding
import com.example.bookkyandroid.ui.fragment.survey.SurveyFragment

class SurveyActivity : BaseActivity<ActivitySurveyBinding>(ActivitySurveyBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(
            R.id.survey_fragmentContainerView_fragmentLayer,
            SurveyFragment()
        )
        transaction.commit()

    }
}