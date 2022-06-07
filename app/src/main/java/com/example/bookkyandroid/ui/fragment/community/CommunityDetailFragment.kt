package com.example.bookkyandroid.ui.fragment.community

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentCommunityPostDetailBinding
import com.example.bookkyandroid.ui.adapter.CommunityDetailCommentAdapter
import com.example.bookkyandroid.ui.adapter.CommunityPostAdapter
import com.example.bookkyandroid.ui.adapter.CommunityQnADetailReplyAdapter
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

class CommunityDetailFragment : BaseFragment<FragmentCommunityPostDetailBinding>(
    FragmentCommunityPostDetailBinding::bind, R.layout.fragment_community_post_detail) {

    val parentID = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isExpanded : Boolean = false
        var communityType = arguments?.getString("communityType").toString()
        val pid = arguments?.getString("PID").toString()
        val bookkyService = ApplicationClass.getInstance().getRetrofit()
        if(communityType == null){
            communityType = "0"
        }

        CoroutineScope(Dispatchers.IO).launch {
            //API 호출 BACK THREAD에서 호출 Coroutine
            getCommunityDetailData(bookkyService, isExpanded,communityType,pid)
        }


        binding.communityCommentsSubmit.setOnClickListener {
            if(binding.communityCommentsEdittext.text.length >0) {
                CoroutineScope(Dispatchers.IO).launch {
                    commentWrite(
                        bookkyService, communityType.toInt(), WriteCommentBodyDataModel(
                            binding.communityCommentsEdittext.text.toString(),
                            parentID,
                            pid.toInt()
                        )
                    )
                    binding.communityCommentsSubmit.text=""
                }
            }
        }



    }

    private fun postAdapterSet(communityDetailCommentDataModel: ArrayList<CommunityDetailCommentDataModel>,communityType: String) {
        binding.communityRecyclerViewComments.adapter = CommunityDetailCommentAdapter(communityDetailCommentDataModel,communityType)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecyclerViewComments.layoutManager = linearLayoutManager
    }
    private fun QnApostAdapterSet(CommunityQnAReplyDetailDataModel: ArrayList<CommunityQnAReplyDetailDataModel>,communityType: String) {
        binding.communityRecyclerViewComments.adapter = CommunityQnADetailReplyAdapter(CommunityQnAReplyDetailDataModel,communityType)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecyclerViewComments.layoutManager = linearLayoutManager
    }


    private fun commentWrite(bookkyService: BookkyService,communityType:Int, commentData : WriteCommentBodyDataModel){
        bookkyService.writeComment(communityType, commentData)
            .enqueue(object : Callback<BaseResponse<String>> {
                override fun onFailure(
                    call: Call<BaseResponse<String>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<BaseResponse<String>>,
                    response: Response<BaseResponse<String>>
                ) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    // 콜 백스택은 아닐텐데 리로드를 어캐해주지?
                    response.body()?.let {
                        getCommunityDetailData(bookkyService, false,communityType.toString(),commentData.PID.toString())
                        //findNavController().popBackStack()
                    }
                }
            })
    }




    private fun getCommunityDetailData(bookkyService:BookkyService, isExpanded: Boolean,communityType : String, pid : String){

        if( communityType != "2") {

            bookkyService.getCommunityDetailData( communityType, pid, 1)
                .enqueue(object : Callback<CommunityDetailResponseDataModel> {
                    override fun onFailure(
                        call: Call<CommunityDetailResponseDataModel>,
                        t: Throwable
                    ) {
                    }

                    override fun onResponse(
                        call: Call<CommunityDetailResponseDataModel>,
                        response: Response<CommunityDetailResponseDataModel>
                    ) {
                        if (response.isSuccessful.not()) {
                            return
                        }
                        response.body()?.let {

                            binding.communityTextViewTitle.text =
                                it.result?.postdata?.title.toString()
                            binding.communityTextViewCreatedAt.text =
                                it.result?.postdata?.updateAt.toString()
                            binding.communityTextViewNickname.text =
                                it.result?.postdata?.nickname.toString()
                            binding.communityTextViewViews.text =
                                "\uD83D\uDC41 " + it.result?.postdata?.views.toString()
                            binding.communityTextViewContents.text =
                                it.result?.postdata?.contents.toString()
                            binding.communityTextViewLikeCnt.text =
                                "좋아요(" + it.result?.postdata?.like?.size.toString() + ")"
                            binding.communityTextViewCommentCnt.text =
                                "댓글(" + it.result?.commentCnt?.toString() + ")"

                            Log.d("Test", it.result?.commentCnt.toString())
                            val length = it.result?.commentCnt?.minus(1)
                            var TempData = ArrayList<CommunityDetailCommentDataModel>()
                            var TempparentID = 0
                            var Tempcomment = ""
                            var TempupdateAt = ""
                            var Templike = ArrayList<Int>()
                            var Tempnickname = ""
                            var Tempthumbnail = ""
                            var TempisAccessible = false
                            var TempCID = 0
                            var Tempreply = 0
                            for (i in 0..length!!) {

                                TempparentID = it.result?.commentdata?.get(i)?.parentID!!
                                Tempcomment = it.result?.commentdata?.get(i)?.comment!!
                                TempupdateAt = it.result?.commentdata?.get(i)?.updateAt!!
                                Templike = it.result?.commentdata?.get(i)?.like!!
                                Tempnickname = it.result?.commentdata?.get(i)?.nickname!!
                                Tempthumbnail = it.result?.commentdata?.get(i)?.thumbnail.toString()
                                TempisAccessible = it.result?.commentdata?.get(i)?.isAccessible!!
                                TempCID = it.result?.commentdata?.get(i)?.CID!!
                                Tempreply = it.result?.commentdata?.get(i)?.reply!!

                                TempData.add(
                                    CommunityDetailCommentDataModel(
                                        TempparentID,
                                        Tempcomment,
                                        TempupdateAt,
                                        Templike,
                                        Tempnickname,
                                        Tempthumbnail,
                                        TempisAccessible,
                                        TempCID,
                                        Tempreply
                                    )
                                )
                            }
                            Log.d("Test", TempData.size.toString())
                            postAdapterSet(TempData, communityType)

                        }
                    }
                })
        }
        else // QNA 파트
        {

            bookkyService.getQnACommunityDetailData( communityType, pid, 1)
                .enqueue(object : Callback<CommunityQnADetailResponseDataModel> {
                    override fun onFailure(
                        call: Call<CommunityQnADetailResponseDataModel>,
                        t: Throwable
                    ) {
                    }

                    override fun onResponse(
                        call: Call<CommunityQnADetailResponseDataModel>,
                        response: Response<CommunityQnADetailResponseDataModel>
                    ) {
                        if (response.isSuccessful.not()) {

                            return
                        }
                        response.body()?.let {

                            binding.communityTextViewTitle.text =
                                it.result?.postdata?.title.toString()
                            binding.communityTextViewCreatedAt.text =
                                it.result?.postdata?.updateAt.toString()
                            binding.communityTextViewNickname.text =
                                it.result?.postdata?.nickname.toString()
                            binding.communityTextViewViews.text =
                                "\uD83D\uDC41 " + it.result?.postdata?.views.toString()
                            binding.communityTextViewContents.text =
                                it.result?.postdata?.contents.toString()
                            binding.communityTextViewLikeCnt.text =
                                "좋아요(" + it.result?.postdata?.like?.size.toString() + ")"
                            binding.communityTextViewCommentCnt.text =
                                "댓글(" + it.result?.commentCnt?.toString() + ")"
                            val length = it.result?.replyCnt?.minus(1)
                            var TempData = ArrayList<CommunityQnAReplyDetailDataModel>()

                            var TempparentID = 0
                            var Temptitle = ""
                            var Tempcontents = ""
                            var Tempviews = 0
                            var TempcreateAt = ""
                            var TempupdateAt = ""
                            var TemppostImage = ArrayList<String>()
                            var Templike = ArrayList<Int>()
                            var Tempnickname = ""
                            var Tempthumbnail = ""
                            var TempisAccessible = false
                            var TempPID = 0
                            var TempTBID = 0
                            var TempisLiked = false
                            var TempparentQPID = 0
                            var TempcommentCnt = 0
                            var TempBook : BookDetailDataModel
                            for (i in 0..length!!) {
// QnA용 Adapter를 하나 만드는게 더 편하겟다..
    // 일단 기본 모양은 댓글 정보가 넘어가는데
        // QnA 에서는 답글 정보가 넘어가야함.
            // QnA xml도 만들어야합니당

                                Temptitle = it.result?.replydata?.get(i)?.title.toString()
                                Tempcontents = it.result?.replydata?.get(i)?.contents!!
                                Tempviews = it.result?.replydata?.get(i)?.views!!
                                TempcreateAt = it.result?.replydata?.get(i)?.createAt!!
                                TempupdateAt = it.result?.replydata?.get(i)?.updateAt!!
                                Templike = it.result?.replydata?.get(i)?.like!!
                                Tempnickname = it.result?.replydata?.get(i)?.nickname!!
                                Tempthumbnail = it.result?.replydata?.get(i)?.thumbnail!!
                                TempisAccessible = it.result?.replydata?.get(i)?.isAccessible!!
                                TempisLiked = it.result?.replydata?.get(i)?.isLiked!!
                                TempTBID = it.result?.replydata?.get(i)?.TBID!!
                                TempparentQPID = it.result?.replydata?.get(i)?.parentQPID!!
                                TempPID = it.result?.replydata?.get(i)?.PID!!
                                TempBook = it.result?.replydata?.get(i)?.Book!!
                                TempcommentCnt = it.result?.replydata?.get(i)?.commentCnt!!
                                TempData.add(
                                    CommunityQnAReplyDetailDataModel(
                                        Temptitle,
                                        Tempcontents,
                                        Tempviews,
                                        TempcreateAt,
                                        TempupdateAt,
                                        Templike,
                                        TempparentQPID,
                                        TemppostImage,
                                        TempTBID,
                                        Tempnickname,
                                        Tempthumbnail,
                                        TempPID,
                                        TempcommentCnt,
                                        TempisLiked,
                                        TempisAccessible,
                                        TempBook,
                                    )
                                )
                            }
                            QnApostAdapterSet(TempData, communityType)


                        }

                    }
                })
        }
    }



}