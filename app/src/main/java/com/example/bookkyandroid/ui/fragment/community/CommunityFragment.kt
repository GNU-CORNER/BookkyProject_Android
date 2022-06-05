package com.example.bookkyandroid.ui.fragment.community

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentCommunityBinding
import com.example.bookkyandroid.ui.adapter.CommunityPostAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(
    FragmentCommunityBinding::bind, R.layout.fragment_community), AdapterView.OnItemSelectedListener {

    var communityType = "0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isExpanded : Boolean = false

        CoroutineScope(Dispatchers.IO).launch {
            //API 호출 BACK THREAD에서 호출 Coroutine
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            getCommunitiyData(bookkyService,communityType)
        }
        binding.communityImageButtonMenu.setOnClickListener{
            binding.planetsSpinner.performClick()
        }
        val list = listOf("자유게시판", "장터게시판", "QnA게시판","핫게시판" ,"내글보기")
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.community_post_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.planetsSpinner.adapter = adapter
            binding.planetsSpinner.onItemSelectedListener = this
        }
        binding.communityButtonWrite.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_communityWriteFragment)
        }
        binding.communityImageButtonSearch.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_communitySearchPostFragment)
        }
    }

    private fun postAdapterSet(myWriting: ArrayList<CommunityAllTypePostDataModel>, communityType: String) {
        binding.communityRecyclerViewPost.adapter = CommunityPostAdapter(myWriting,communityType)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecyclerViewPost.layoutManager = linearLayoutManager
    }

    private fun getCommunitiyData(bookkyService: BookkyService, communityType : String){

        if (communityType != "4") {
            bookkyService.getCommunitiyData( communityType, 25, 1)
                .enqueue(object : Callback<CommunityResponseDataModel> {
                    override fun onFailure(call: Call<CommunityResponseDataModel>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<CommunityResponseDataModel>,
                        response: Response<CommunityResponseDataModel>
                    ) {
                        if (response.isSuccessful.not()) {

                            return
                        }
                        response.body()?.let {
                            if (communityType == "0")
                                binding.communityTextViewMenu.text = "자유게시판"
                            else if (communityType == "1")
                                binding.communityTextViewMenu.text = "책장터 게시판"
                            else if (communityType == "2")
                                binding.communityTextViewMenu.text = "Q&A 게시판"
                            else if (communityType == "3")
                                binding.communityTextViewMenu.text = "내 글 보기"
                            var Temptitle = ""
                            var Tempcontents = ""
                            var Tempcomment = 0
                            var Templike = 0
                            var TempPID = 0
                            var i = 0
                            val length = it.result?.postList?.size.toString().toInt() - 1
                            if (communityType == "0" || communityType == "1") {
                                // 자게, 장터 =-> 그대로
                                var TempData = ArrayList<CommunityAllTypePostDataModel>()
                                for (i in 0..length) {
                                    Temptitle = it.result?.postList?.get(i)?.title.toString()
                                    Tempcontents = it.result?.postList?.get(i)?.contents.toString()
                                    Tempcomment = it.result?.postList?.get(i)?.commentCnt.toString().toInt()
                                    Templike = it.result?.postList?.get(i)?.likeCnt.toString().toInt()
                                    TempPID = it.result?.postList?.get(i)?.PID.toString().toInt()
                                    TempData.add(
                                        CommunityAllTypePostDataModel(
                                            title = Temptitle,
                                            contents = Tempcontents,
                                            likeCnt = Templike,
                                            commentCnt = Tempcomment,
                                            PID = TempPID,
                                            communityType = communityType.toInt(),
                                            replyCnt = -1
                                        )
                                    )
                                }


                                postAdapterSet(TempData, communityType)
                            } 
                            else if (communityType == "2") {
                                // QNA 스타일
                                var TempData = ArrayList<CommunityAllTypePostDataModel>()
                                var TempreplyCnt = 0
                                for (i in 0..length) {
                                    Temptitle = it.result?.postList?.get(i)?.title.toString()
                                    Tempcontents = it.result?.postList?.get(i)?.contents.toString()
                                    Tempcomment = it.result?.postList?.get(i)?.commentCnt.toString().toInt()
                                    Templike = it.result?.postList?.get(i)?.likeCnt.toString().toInt()
                                    TempPID = it.result?.postList?.get(i)?.PID.toString().toInt()
                                    TempreplyCnt = it.result?.postList?.get(i)?.replyCnt!!
                                    TempData.add(
                                        CommunityAllTypePostDataModel(
                                            title = Temptitle,
                                            contents = Tempcontents,
                                            likeCnt = Templike,
                                            commentCnt = Tempcomment,
                                            PID = TempPID,
                                            communityType = communityType.toInt(),
                                            replyCnt = TempreplyCnt
                                        )
                                    )
                                }


                                postAdapterSet(TempData, communityType)
                            } 
                            else {
                                // 내글 + 핫게
                                var TempData = ArrayList<CommunityAllTypePostDataModel>()
                                var TempreplyCnt = 0
                                var TempCommunityType = 0
                                for (i in 0..length) {
                                    Temptitle = it.result?.postList?.get(i)?.title.toString()
                                    Tempcontents = it.result?.postList?.get(i)?.contents.toString()
                                    Tempcomment = it.result?.postList?.get(i)?.commentCnt.toString().toInt()
                                    Templike = it.result?.postList?.get(i)?.likeCnt.toString().toInt()
                                    TempPID = it.result?.postList?.get(i)?.PID.toString().toInt()
                                    TempreplyCnt = it.result?.postList?.get(i)?.replyCnt!!
                                    TempCommunityType = it.result?.postList?.get(i)?.communityType!!

                                    TempData.add(
                                        CommunityAllTypePostDataModel(
                                            title = Temptitle,
                                            contents = Tempcontents,
                                            likeCnt = Templike,
                                            commentCnt = Tempcomment,
                                            PID = TempPID,
                                            communityType = TempCommunityType,
                                            replyCnt = TempreplyCnt
                                        )
                                    )
                                }


                                postAdapterSet(TempData, communityType)
                            }
                        }
                    }
                })
        }
        else
        {
            bookkyService.getHotCommunitiyData(25, 1)
                .enqueue(object : Callback<CommunityResponseDataModel> {
                    override fun onFailure(call: Call<CommunityResponseDataModel>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<CommunityResponseDataModel>,
                        response: Response<CommunityResponseDataModel>
                    ) {
                        if (response.isSuccessful.not()) {

                            return
                        }
                        response.body()?.let {
                            binding.communityTextViewMenu.text = "H\uD83D\uDD25T 게시판"
                            var Temptitle = ""
                            var Tempcontents = ""
                            var Tempcomment = 0
                            var Templike = 0
                            var TempPID = 0
                            var i = 0
                            val length = it.result?.postList?.size.toString().toInt() - 1
                                // 핫게=> communityAlltype
                                var TempData = ArrayList<CommunityAllTypePostDataModel>()
                                var TempreplyCnt = 0
                                var TempCommunityType = 0
                                for (i in 0..length) {
                                    Temptitle = it.result?.postList?.get(i)?.title.toString()
                                    Tempcontents = it.result?.postList?.get(i)?.contents.toString()
                                    Tempcomment =
                                        it.result?.postList?.get(i)?.commentCnt.toString().toInt()
                                    Templike =
                                        it.result?.postList?.get(i)?.likeCnt.toString().toInt()
                                    TempPID = it.result?.postList?.get(i)?.PID.toString().toInt()
                                    TempreplyCnt = it.result?.postList?.get(i)?.replyCnt!!
                                    TempCommunityType =
                                        it.result?.postList?.get(i)?.communityType!!.toInt()

                                    TempData.add(
                                        CommunityAllTypePostDataModel(
                                            title = Temptitle,
                                            contents = Tempcontents,
                                            likeCnt = Templike,
                                            commentCnt = Tempcomment,
                                            PID = TempPID,
                                            communityType = TempCommunityType,
                                            replyCnt = TempreplyCnt
                                        )
                                    )
                                }

                                postAdapterSet(TempData, communityType)



                            // postList => Arraylist <CommunityPostDataModel> PID, title, contents
                            // userData => Arraylist <CommunityUserDataModel>
                            // subData =>  Arraylist <CommunitySubDataModel>
                        }

                    }
                })


        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0) {
                //Hot 게시판 정보 받아오는거

            CoroutineScope(Dispatchers.IO).launch {
                //API 호출 BACK THREAD에서 호출 Coroutine
                val bookkyService = ApplicationClass.getInstance().getRetrofit()
                getCommunitiyData(bookkyService, "0")
            }

        }
        else if (position == 1) {
              //Hot 게시판 정보 받아오는거

            CoroutineScope(Dispatchers.IO).launch {
                //API 호출 BACK THREAD에서 호출 Coroutine
                val bookkyService = ApplicationClass.getInstance().getRetrofit()
                getCommunitiyData(bookkyService, "4")
            }
          }
        else if (position == 2) {
            //Hot 게시판 정보 받아오는거

            CoroutineScope(Dispatchers.IO).launch {
                //API 호출 BACK THREAD에서 호출 Coroutine
                val bookkyService = ApplicationClass.getInstance().getRetrofit()
                getCommunitiyData(bookkyService, "2")
            }
        }
        else if (position == 3) {
            //Hot 게시판 정보 받아오는거

            CoroutineScope(Dispatchers.IO).launch {
                //API 호출 BACK THREAD에서 호출 Coroutine
                val bookkyService = ApplicationClass.getInstance().getRetrofit()
                getCommunitiyData(bookkyService, "1")
            }
        }
        else if (position == 4) {
            //Hot 게시판 정보 받아오는거

            CoroutineScope(Dispatchers.IO).launch {
                //API 호출 BACK THREAD에서 호출 Coroutine
                val bookkyService = ApplicationClass.getInstance().getRetrofit()
                getCommunitiyData(bookkyService, "3")
            }
        }




    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}