package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentHomeBinding
import com.example.bookkyandroid.ui.adapter.HomeCommunityShortAdapter
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        showLoadingDialog(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            //API 호출 BACK THREAD에서 호출 Coroutine
            val bookkyService = RetrofitManager.getInstance().bookkyService
            val access_token = ApplicationClass.getInstance().getDataStore().accessToken.first()
            getHomeData(bookkyService, access_token)
        }
        binding.textViewHomeCommunityHeadLineText.setOnClickListener {
            var flag = false
            CoroutineScope(Dispatchers.IO).launch {
                if (ApplicationClass.getInstance().getDataStore().accessToken.first() == null){
                    flag = true
                }
            }
            if (!flag){
//                findNavController().navigate(R.id.c)
            }
            else{
                findNavController().navigate(R.id.action_global_signInFragment)
            }
        }
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_surveyFragment)
        }
        binding.textViewHomeTagMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeMoreTagFragment)
        }
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_global_signInFragment)
        }
        binding.frameLayoutBookRecommendRoadmapButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_suggestionFragment)
        }
        binding.frameLayoutRecommendBookynatorButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bookRecommendFragment)
        }
    }
    private fun successToGetHome(nickname : String){
        binding.textViewHomeNickName.setText(nickname)
    }
    private fun homeBookListAdapterSet1(headline : String, DataModels: HomeBookListDataModel){
        binding.homeTextViewRecyclerviewHeadline1.text = headline
        binding.recyclerViewHomeBookList1.adapter = HomeTagByBooksAdapter(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList1.layoutManager = linearLayoutManager
        binding.homeTextViewRecyclerviewHeadline1.setOnClickListener {
            val bundle = bundleOf("TID" to DataModels.TMID)
            it.findNavController().navigate(R.id.homeMoreTagDetailFragment, bundle)
        }
    }

    private fun homeBookListAdapterSet2(headline : String,DataModels: HomeBookListDataModel){
        binding.homeTextViewRecyclerviewHeadline2.text = headline
        binding.recyclerViewHomeBookList2.adapter = HomeTagByBooksAdapter(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList2.layoutManager = linearLayoutManager
        binding.homeTextViewRecyclerviewHeadline1.setOnClickListener {
            val bundle = bundleOf("TID" to DataModels.TMID)
            it.findNavController().navigate(R.id.homeMoreTagDetailFragment, bundle)
        }
    }

    private fun homeCommunitySet(titles: ArrayList<HomeCommunityDataModel>) {
        binding.recyclerViewHomeCommunityList.adapter = HomeCommunityShortAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHomeCommunityList.layoutManager = linearLayoutManager
        binding.recyclerViewHomeCommunityList.stopScroll()
    }
    companion object{
        private const val TAG="HomeFragment"
    }

    private fun getHomeData(bookkyService: BookkyService, access_token : String){
        bookkyService.getHomeData(access_token)
            .enqueue(object : Callback<HomeResponseDataModel> {
                override fun onFailure(call: Call<HomeResponseDataModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<HomeResponseDataModel>, response: Response<HomeResponseDataModel>){
                    if (response.code() == 401) {
//                        CoroutineScope(Dispatchers.IO).launch {
//                            val refresh_token =
//                                ApplicationClass.getInstance().getDataStore().refreshToken.first()
//                            TokenManager.getInstance()
//                                .refresh_token(bookkyService, access_token, refresh_token)
//                            val access_token =
//                                ApplicationClass.getInstance().getDataStore().accessToken.first()
//                            runBlocking {
//                                getHomeData(bookkyService, access_token)
//                            }
//                        }

                    }
                    response.body()?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            successToGetHome(it.result.userData!!.nickname)
                            homeCommunitySet(it.result.communityList!!)
                            homeBookListAdapterSet1(it.result.bookList!![0].tag,
                                it.result.bookList!![0]
                            )
                            homeBookListAdapterSet2(it.result.bookList!![1].tag,
                                it.result.bookList!![1]
                            )
                        }
//                        Thread.sleep(1000)
//                        dismissLoadingDialog()
                    }
                }
            })
    }
}
