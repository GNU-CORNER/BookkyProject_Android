package com.example.bookkyandroid.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentHomeBinding
import com.example.bookkyandroid.ui.adapter.HomeCommunityShortAdapter
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isExpanded : Boolean = false

        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getHomeData(retrofit, isExpanded,this.activity?.intent?.getStringExtra("access-token").toString())
        var textNickname: TextView = binding.textViewHomeNickName
        textNickname.text = "황랑귀"
        val bookList = arrayListOf<String>("test1", "test2", "test3","test3","test3","test3","test3")
        val communityDummyData = arrayListOf<HomeCommunityDataModel>(
            HomeCommunityDataModel("핫게시판", "한 번 읽어보고 마스터한 책 사실 분~!!!"),
            HomeCommunityDataModel("QnA게시판", "함수를 썼는데 이상해요..."),
            HomeCommunityDataModel("자유게시판", "카카오 공채 떴던데 보신 분 있으신가요?")
        )
        homeCommunitySet(communityDummyData)

        binding.textViewHomeTagMore.setOnClickListener {
            isExpanded = if (!isExpanded) {
                getHomeData(retrofit, isExpanded,this.activity?.intent?.getStringExtra("access-token").toString())
                true
            } else {
                getHomeData(retrofit, isExpanded,this.activity?.intent?.getStringExtra("access-token").toString())
                false
            }
        }


    }

    private fun homeBookListAdapterSet(DataModels: ArrayList<HomeBookListDataModel>, isExpanded : Boolean) {
        binding.recyclerViewHomeBookList.adapter = HomeTagByBooksAdapter(DataModels, isExpanded)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHomeBookList.layoutManager = linearLayoutManager
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
    public interface HomeGetCaller {

        @GET("/v1/home")
        fun getHomeData(
            @Header("access-token") access_token: String?
        ): Call<HomeResponseDataModel>
    }
    private fun getHomeData(retrofit: Retrofit, isExpanded: Boolean, access_token : String){
        val bookService = retrofit.create(HomeFragment.HomeGetCaller::class.java)
        bookService.getHomeData(access_token)
            .enqueue(object : Callback<HomeResponseDataModel> {
                override fun onFailure(call: Call<HomeResponseDataModel>, t: Throwable) {
                    Log.d(HomeFragment.TAG, t.toString())
                }

                override fun onResponse(call: Call<HomeResponseDataModel>, response: Response<HomeResponseDataModel>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        Log.d(HomeFragment.TAG, it.toString())

                        it.result?.bookList?.forEach { data->
                            Log.d(TAG, data.tag)
                        }
                        binding.textViewHomeNickName.text = it.result!!.userData!!.nickname
                        homeBookListAdapterSet(it.result!!.bookList!!, isExpanded)
                    }
                }
            })
    }
}
