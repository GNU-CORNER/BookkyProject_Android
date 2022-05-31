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
                    Log.d("TEss", t.toString())
                }

                override fun onResponse(call: Call<CommunityResponseDataModel>, response: Response<CommunityResponseDataModel>){
                    Log.d("TEss","Check2-3")
                    if (response.isSuccessful.not()) {
                        return
                    }
                    Log.d("TEss","Check2-4")
                    response.body()?.let {
                        Log.d("TEss","Check2-5")
                        var Temptitle=""
                        var Tempcontents=""
                        var Tempcomment = 0
                        var Templike = 0
                        var TempPID = 0
                        var i = 0
                        val length = it.result?.postList?.size.toString().toInt() - 1
                        Log.d("TEss","Check2-6")
                        var TempData = ArrayList<MyWriting>()
                        Log.d("TEss","Check2-7")
                        Log.d("TEss",length.toString())
                        for( i in 0..length)
                        {
                            Log.d("TEss","Check2-8")
                            Temptitle=it.result?.postList?.get(i)?.title.toString()
                            Log.d("TEss",Temptitle)
                            Tempcontents=it.result?.postList?.get(i)?.contents.toString()
                            Log.d("TEss",Tempcontents)
                            Tempcomment=it.result?.subData?.get(i)?.commentCnt.toString().toInt()
                            Log.d("TEss",Tempcomment.toString())
                            Templike=it.result?.subData?.get(i)?.likeCnt.toString().toInt()
                            Log.d("TEss",Templike.toString())
                            TempPID=it.result?.postList?.get(i)?.APID.toString().toInt()
                            Log.d("TEss",TempPID.toString())
                            TempData.add(MyWriting(Temptitle,Tempcontents,Tempcomment,Templike,TempPID))
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