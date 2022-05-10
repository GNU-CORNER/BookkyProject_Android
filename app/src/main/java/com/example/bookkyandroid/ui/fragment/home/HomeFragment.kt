package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentHomeBinding
import com.example.bookkyandroid.ui.activity.login.LoginActivity
import com.example.bookkyandroid.ui.adapter.HomeCommunityShortAdapter
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter
import com.example.bookkyandroid.ui.fragment.signup.SignupFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var accessToken : String = ""
        CoroutineScope(Dispatchers.Main).launch {
            accessToken = ApplicationClass.getInstance().getDataStore().accessToken.first()
            Log.d(TAG, "onViewCreated: "+accessToken)
        }
        val bookkyService = RetrofitManager.getInstance().bookkyService
        var textNickname: TextView = binding.textViewHomeNickName
        textNickname.text = "황랑귀"

        val communityDummyData = arrayListOf<HomeCommunityDataModel>(
            HomeCommunityDataModel("핫게시판", "한 번 읽어보고 마스터한 책 사실 분~!!!"),
            HomeCommunityDataModel("QnA게시판", "함수를 썼는데 이상해요..."),
            HomeCommunityDataModel("자유게시판", "카카오 공채 떴던데 보신 분 있으신가요?")
        )
        homeCommunitySet(communityDummyData)
        getHomeData(bookkyService, accessToken)

        binding.textViewHomeTagMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeMoreTagFragment)
        }
        binding.textViewHomeRecommendText.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginActivity)
        }
    }

    private fun homeBookListAdapterSet1(headline : String, DataModels: HomeBookListDataModel){
        binding.homeTextViewRecyclerviewHeadline1.text = headline
        binding.recyclerViewHomeBookList1.adapter = HomeTagByBooksAdapter(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList1.layoutManager = linearLayoutManager
    }

    private fun homeBookListAdapterSet2(headline : String,DataModels: HomeBookListDataModel){
        binding.homeTextViewRecyclerviewHeadline2.text = headline
        binding.recyclerViewHomeBookList2.adapter = HomeTagByBooksAdapter(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList2.layoutManager = linearLayoutManager
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
                    Log.d(HomeFragment.TAG, t.toString())
                }

                override fun onResponse(call: Call<HomeResponseDataModel>, response: Response<HomeResponseDataModel>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {

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
