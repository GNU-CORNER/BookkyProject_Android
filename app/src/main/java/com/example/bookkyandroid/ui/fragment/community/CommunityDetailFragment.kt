package com.example.bookkyandroid.ui.fragment.community

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.CommunityDetailResponseDataModel
import com.example.bookkyandroid.databinding.FragmentCommunityPostDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class CommunityDetailFragment : BaseFragment<FragmentCommunityPostDetailBinding>(
    FragmentCommunityPostDetailBinding::bind, R.layout.fragment_community_post_detail) {

    val args: CommunityDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("TEss",args.pid.toString())

        var isExpanded : Boolean = false
        CoroutineScope(Dispatchers.IO).launch {
            //API 호출 BACK THREAD에서 호출 Coroutine
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            val access_token = ApplicationClass.getInstance().getDataStore().accessToken.first()
            getCommunityDetailData(bookkyService, isExpanded,access_token)
        }

    }


    private fun getCommunityDetailData(bookkyService:BookkyService, isExpanded: Boolean, access_token : String){
        Log.d("TEss","Check2-1")
        bookkyService.getCommunityDetailData("0",args.pid.toString())
            .enqueue(object : Callback<CommunityDetailResponseDataModel> {
                override fun onFailure(call: Call<CommunityDetailResponseDataModel>, t: Throwable) {
                    Log.d("TEss", "실패!!")
                    Log.d("TEss", t.toString())
                }

                override fun onResponse(call: Call<CommunityDetailResponseDataModel>, response: Response<CommunityDetailResponseDataModel>){
                    Log.d("TEss","Check2-3")
                    if (response.isSuccessful.not()) {
                        Log.d("TEss","Check2-3-1 error")
                        return
                    }
                    Log.d("TEss","Check2-4")
                    response.body()?.let {
                        Log.d("TEss",it.result?.postdata.toString())
                        binding.communityTextViewTitle.text = it.result?.postdata?.title.toString()
                        binding.communityTextViewCreatedAt.text = it.result?.postdata?.updateAt.toString()
                        binding.communityTextViewNickname.text = it.result?.postdata?.nickname.toString()
                        binding.communityTextViewViews.text = "\uD83D\uDC41" + it.result?.postdata?.views.toString()
                        binding.communityTextViewContents.text = it.result?.postdata?.contents.toString()
                        binding.communityTextViewLikeCnt.text = "좋아요("+it.result?.postdata?.like?.size.toString()+")"
                        binding.communityTextViewCommentCnt.text = "댓글("+it.result?.commentCnt?.toString()+")"

//                        var Temptitle=""
//                        var Tempcontents=""
//                        var Tempcomment = 0
//                        var Templike = 0
//                        var TempPID = 0
//                        var i = 0
//                        val length = it.result?.postList?.size.toString().toInt() - 1
//                        Log.d("TEss","Check2-6")
//                        var TempData = ArrayList<MyWriting>()
//                        Log.d("TEss","Check2-7")
//                        Log.d("TEss",length.toString())
//                        for( i in 0..length)
//                        {
//                            Log.d("TEss","Check2-8")
//                            Temptitle=it.result?.postList?.get(i)?.title.toString()
//                            Log.d("TEss",Temptitle)
//                            Tempcontents=it.result?.postList?.get(i)?.contents.toString()
//                            Log.d("TEss",Tempcontents)
//                            Tempcomment=it.result?.subData?.get(i)?.commentCnt.toString().toInt()
//                            Log.d("TEss",Tempcomment.toString())
//                            Templike=it.result?.subData?.get(i)?.likeCnt.toString().toInt()
//                            Log.d("TEss",Templike.toString())
//                            TempPID=it.result?.postList?.get(i)?.APID.toString().toInt()
//                            Log.d("TEss",TempPID.toString())
//                            TempData.add(MyWriting(Temptitle,Tempcontents,Tempcomment,Templike,TempPID))
//                        }
//
//                        Log.d("TEss",TempData.toString())
                        //postAdapterSet(TempData,findNavController())

                        // postList => Arraylist <CommunityPostDataModel> PID, title, contents
                        // userData => Arraylist <CommunityUserDataModel>
                        // subData =>  Arraylist <CommunitySubDataModel>
                    }

                }
            })
        Log.d("TEss","Check2-8")
    }



}