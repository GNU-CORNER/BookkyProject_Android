package com.example.bookkyandroid.ui.fragment.myinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentMyCommunityBinding
import com.example.bookkyandroid.ui.adapter.CommunityPostAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoPostAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileCommunityFragment :BaseFragment<FragmentMyCommunityBinding>(
    FragmentMyCommunityBinding::bind, R.layout.fragment_my_community) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingDialog(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            val bookkService = RetrofitManager.getInstance().bookkyService
            val access_token = ApplicationClass.getInstance().getDataStore().accessToken.first()
            getMyPostList(bookkService,access_token)
        }
    }
    private fun myPostRecyclerViewBinder(communityList:ArrayList<MyProfilePostDataModel>){
        binding.recyclerviewMyPost.adapter = MyInfoPostAdapter(communityList)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerviewMyPost.layoutManager = linearLayoutManager
    }
    private fun getMyPostList(bookkyService: BookkyService, access_token : String){
        bookkyService.getMyPost(access_token)
            .enqueue(object : Callback<BaseResponse<MyProfilePostResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<MyProfilePostResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<MyProfilePostResponseDataModel>>, response: Response<BaseResponse<MyProfilePostResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        myPostRecyclerViewBinder(it.result.communityList)
                        Thread.sleep(1000)
                        dismissLoadingDialog()
                    }
                }
            })
    }
}