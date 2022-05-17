package com.example.bookkyandroid.ui.fragment.home

import android.content.Intent
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Default).launch {
            val bookkyService = RetrofitManager.getInstance().bookkyService
            RetrofitManager.getInstance().getToken()
            getHomeData(bookkyService)
        }

        var textNickname: TextView = binding.textViewHomeNickName
        textNickname.text = "황랑귀"

        val communityDummyData = arrayListOf<HomeCommunityDataModel>(
            HomeCommunityDataModel("핫게시판", "한 번 읽어보고 마스터한 책 사실 분~!!!"),
            HomeCommunityDataModel("QnA게시판", "함수를 썼는데 이상해요..."),
            HomeCommunityDataModel("자유게시판", "카카오 공채 떴던데 보신 분 있으신가요?")
        )
        homeCommunitySet(communityDummyData)

        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_surveyFragment)
        }
        binding.textViewHomeTagMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeMoreTagFragment)
        }
        binding.textViewHomeRecommendText.setOnClickListener {
            findNavController().navigate(R.id.action_global_signInFragment)
        }
        binding.frameLayoutBookRecommendRoadmapButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_suggestionFragment)
        }
        binding.frameLayoutRecommendBookynatorButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bookRecommendFragment)
        }
    }

    private fun homeBookListAdapterSet1(headline : String, DataModels: HomeBookListDataModel){
        binding.homeTextViewRecyclerviewHeadline1.text = headline
        binding.recyclerViewHomeBookList1.adapter = HomeTagByBooksAdapter(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList1.layoutManager = linearLayoutManager
        binding.homeTextViewRecyclerviewHeadline1.setOnClickListener {
            val bundle = bundleOf("TID" to DataModels.TID)
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
            val bundle = bundleOf("TID" to DataModels.TID)
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

    private fun getHomeData(bookkyService: BookkyService){
        val accessToken = RetrofitManager.getInstance().accessToken
        val refreshToken = RetrofitManager.getInstance().refreshToken
        bookkyService.getHomeData(accessToken)
            .enqueue(object : Callback<HomeResponseDataModel> {
                override fun onFailure(call: Call<HomeResponseDataModel>, t: Throwable) {
                    TokenManager().refresh_token(bookkyService,accessToken, refreshToken)
                }

                override fun onResponse(call: Call<HomeResponseDataModel>, response: Response<HomeResponseDataModel>){
                    if (response.isSuccessful.not()) {
                        TokenManager().refresh_token(bookkyService,accessToken, refreshToken)
                        getHomeData(bookkyService)
                        return
                    }
                    response.body()?.let {
                         binding.textViewHomeNickName.text= it.result!!.userData!!.nickname
                        homeBookListAdapterSet1(it.result!!.bookList!![0].tag,
                            it.result!!.bookList!![0]
                        )
                        homeBookListAdapterSet2(it.result!!.bookList!![1].tag,
                            it.result!!.bookList!![1]
                        )
                    }
                }
            })
    }
}
