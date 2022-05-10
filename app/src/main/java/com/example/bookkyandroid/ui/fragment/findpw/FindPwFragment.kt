package com.example.bookkyandroid.ui.fragment.findpw

import android.os.Bundle
import android.view.View
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.databinding.FragmentFindpwBinding

class FindPwFragment : BaseFragment<FragmentFindpwBinding>(FragmentFindpwBinding::bind, R.layout.fragment_findpw) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    val transaction = parentFragmentManager.beginTransaction()
        binding.findpwButtonNext!!.setOnClickListener{
            transaction.replace(R.id.login_fragmentContainerView_fragmentLayer, FindPwNewPwFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.findpwButtonCallCode!!.setOnClickListener{

        }
        binding.findpwButtonCheckCode!!.setOnClickListener{

        }
    }
}