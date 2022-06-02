package com.example.bookkyandroid.ui.fragment.community

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentCommunityBinding
import com.example.bookkyandroid.ui.adapter.CommunityPostAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(
    FragmentCommunityBinding::bind, R.layout.fragment_community) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TEss","Check1")
        var isExpanded : Boolean = false

        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d("TEss","Check2")
        getCommunitiyData(retrofit, isExpanded)
        Log.d("TEss","Check3")
        binding.communityButtonWrite.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_communityWriteFragment)
        }
        Log.d("TEss","Check4")
        binding.communityImageButtonSearch.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_communitySearchPostFragment)
        }
        Log.d("TEss","Check5")

    }

    private fun postAdapterSet(myWriting: ArrayList<MyWriting>, NavControl : NavController) {
        binding.communityRecyclerViewPost.adapter = CommunityPostAdapter(myWriting, NavControl)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecyclerViewPost.layoutManager = linearLayoutManager
    }

    public interface CommunityGetCaller {
        @GET("/v1/community/postlist/0")
        fun getCommunitiyData(
            @Header("access-token") access_token: String?
        ): Call<CommunityResponseDataModel>
    }

    private fun getCommunitiyData(retrofit: Retrofit, isExpanded: Boolean){
        val communityService = retrofit.create(CommunityFragment.CommunityGetCaller::class.java)
        val accessToken = ""
        communityService.getCommunitiyData(accessToken)
            .enqueue(object : Callback<CommunityResponseDataModel> {
                override fun onFailure(call: Call<CommunityResponseDataModel>, t: Throwable) {
                }

                override fun onResponse(call: Call<CommunityResponseDataModel>, response: Response<CommunityResponseDataModel>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        var Temptitle=""
                        var Tempcontents=""
                        var Tempcomment = 0
                        var Templike = 0
                        var TempPID = 0
                        var i = 0
                        val length = it.result?.postList?.size.toString().toInt() - 1
                        var TempData = ArrayList<MyWriting>()
                        for( i in 0..length)
                        {
                            Temptitle=it.result?.postList?.get(i)?.title.toString()
                            Tempcontents=it.result?.postList?.get(i)?.contents.toString()
                            Tempcomment=it.result?.postList?.get(i)?.commentCnt.toString().toInt()
                            Templike=it.result?.postList?.get(i)?.likeCnt.toString().toInt()
                            TempPID=it.result?.postList?.get(i)?.PID.toString().toInt()
                            TempData.add(MyWriting(Temptitle,Tempcontents,Templike,Tempcomment,TempPID))
                        }

                        Log.d("TEss",TempData.toString())
                        postAdapterSet(TempData,findNavController())

                        // postList => Arraylist <CommunityPostDataModel> PID, title, contents
                        // userData => Arraylist <CommunityUserDataModel>
                        // subData =>  Arraylist <CommunitySubDataModel>
                    }

                }
            })
    }


}