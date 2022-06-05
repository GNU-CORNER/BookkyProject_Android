package com.example.bookkyandroid.ui.fragment.book

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.databinding.FragmentBookDetailBinding
import com.example.bookkyandroid.ui.adapter.BookDetailReviewAdapter
import com.example.bookkyandroid.ui.adapter.MyInfoInterestedAreaAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep


class BookDetailFragment: BaseFragment<FragmentBookDetailBinding>(FragmentBookDetailBinding::bind, R.layout.fragment_book_detail), callbackMoreAPI {
    var bookID : Int? = 0
    var flag = true
    var data:BookDetailResponseDataModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationClass.getInstance().showLoadingDialog(requireContext())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookID = arguments?.getInt("BID")

        var expand1 = false
        var expand2 = false
        CoroutineScope(Dispatchers.IO).launch {
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            getBookDetail(bookkyService,bookID!!)
        }
        if(TokenManager.getInstance().access_token.length == 0) {
            binding.bookyDetailTextViewWriteReview.setTextColor(Color.GRAY)
        }
        binding.bookDetailImageBtnLike.setOnClickListener {
            if(TokenManager.getInstance().access_token.length != 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    val bookkyService = ApplicationClass.getInstance().getRetrofit()
                    postFavorite(bookkyService, bookID!!)
                }
            }
            else{
                findNavController().navigate(R.id.action_global_signInFragment)
            }
        }
        binding.bookDetailTextViewExpand1.setOnClickListener {
            if(!expand1){
                binding.bookDetailTextViewIntro.maxLines = 9999
                binding.bookDetailTextViewExpand1.text = "접기 <"
                expand1 = true
            }
            else{
                binding.bookDetailTextViewIntro.maxLines = 4
                binding.bookDetailTextViewExpand1.text = "펼처보기 >"
                expand1 = false
            }
        }
        binding.bookDetailTextViewIntro.setOnClickListener {
            binding.bookDetailTextViewIntro.maxLines = 4
            binding.bookDetailTextViewExpand1.text = "펼처보기 >"
            expand1 = false
        }
        binding.bookDetailTextViewExpand2.setOnClickListener {
            if(!expand2){
                binding.bookDetailTextViewIndex.maxLines = 9999
                binding.bookDetailTextViewExpand2.text = "접기 <"
                expand2 = true
            }
            else{
                binding.bookDetailTextViewIndex.maxLines = 4
                binding.bookDetailTextViewExpand2.text = "펼처보기 >"
                expand2 = false
            }
        }
        binding.bookDetailTextViewIndex.setOnClickListener {

            binding.bookDetailTextViewIndex.maxLines = 4
            binding.bookDetailTextViewExpand2.text = "펼처보기 >"
            expand2 = false
        }


    }

    override fun callAPI() {
        ApplicationClass.getInstance().showLoadingDialog(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            getBookDetail(bookkyService, bookID!!)
        }

    }
    private fun successToCallGet(image : String, TITLE : String, AUTHOR : String, RATING : Float, BID:Int) {

        binding.bookyDetailTextViewWriteReview.setOnClickListener {
            if(TokenManager.getInstance().access_token.length != 0) {
                if (flag) {
                    val bundle = bundleOf("thumbnail" to image)
                    bundle.putString("TITLE", TITLE)
                    bundle.putString("AUTHOR", AUTHOR)
                    bundle.putFloat("RATING", RATING)
                    bundle.putInt("BID", BID)
                    bundle.putInt("type", 1)
                    findNavController().navigate(
                        R.id.action_bookDetailFragment_to_reviewWriteFragment,
                        bundle
                    )
                } else {
                    showCustomToast("이미 작성한 리뷰가 있습니다.")
                }
            }
            else{
                findNavController().navigate(R.id.action_global_signInFragment)
            }
        }
        sleep(500)
        ApplicationClass.getInstance().dismissLoadingDialog()
    }
    private fun tagAdapterSet(tags: ArrayList<TagDataResponseDataModel>) {
        binding.bookDetailRecyclerViewTags.adapter = MyInfoInterestedAreaAdapter(tags)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.bookDetailRecyclerViewTags.layoutManager = linearLayoutManager
    }


    private fun reviewAdapterSet(review: ArrayList<ReviewDataModel>) {
        var reviewData = arrayListOf<ReviewDataModel>()
        if(review.size == 0){
            reviewData.add(ReviewDataModel(0,0,0,"",0,"",0.0F,false,0,false,"","","",""))
        }
        else{
            flag = false
            reviewData = review
        }
        binding.bookDetailRecyclerViewReviews.adapter = BookDetailReviewAdapter(reviewData, requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.bookDetailRecyclerViewReviews.layoutManager = linearLayoutManager
    }
    private fun successToCallFavorite(isFavorite : Boolean){
        if (isFavorite) {
            binding.bookDetailImageBtnLike.background.setTint(Color.RED)
        }
        else{
            binding.bookDetailImageBtnLike.background.setTint(Color.DKGRAY)
        }

    }

    private fun postFavorite(bookkyService: BookkyService, pk:Int){
        bookkyService.favoriteBook(pk)
            .enqueue(object : Callback<BaseResponse<PostFavoriteBookDataModel>> {
                override fun onFailure(call: Call<BaseResponse<PostFavoriteBookDataModel>>, t: Throwable) {
                }

                override fun onResponse(call: Call<BaseResponse<PostFavoriteBookDataModel>>, response: Response<BaseResponse<PostFavoriteBookDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        successToCallFavorite(it.result.isFavorite)
                    }

                }
            })
    }
    private fun splitter(longText : String) : String{
        var text : String = ""
        text = longText.replace("^^", "\n")

        return text
    }
    private fun getBookDetail(bookkyService: BookkyService, pk:Int){

        bookkyService.getBookDetail(pk)
            .enqueue(object : Callback<BaseResponse<BookDetailResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<BookDetailResponseDataModel>>, t: Throwable) {
                    Log.d("asasdsad", t.toString())
                }

                override fun onResponse(call: Call<BaseResponse<BookDetailResponseDataModel>>, response: Response<BaseResponse<BookDetailResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        Log.d("falseA", response.toString())
                        return
                    }
                    Log.d("out response", response.toString())
                    response.body()?.let {

                        Log.d("log", it.result.toString())
                        binding.bookDetailTextViewTitle.text = it.result.bookList!!.TITLE
                        binding.bookDetailTextViewWriter.text = it.result.bookList!!.AUTHOR + " / "+ it.result.bookList!!.PUBLISHER
                        binding.bookDetailTextViewInfo1.text =
                            it.result.bookList!!.PUBLISHER
                        binding.bookDetailTextViewInfo2.text =
                            it.result.bookList!!.AUTHOR
                        binding.bookDetailTextViewInfo3.text = it.result.bookList!!.PRICE
                        binding.bookDetailTextViewInfo4.text = it.result.bookList!!.PAGE
                        binding.bookDetailTextviewinfo5.text = it.result.bookList!!.ISBN
                        if (it.result.bookList!!.BOOK_INTRODUCTION == null){
                            binding.bookDetailTextViewIntro.text = "책 소개가 없습니다."
                        }else{
                            binding.bookDetailTextViewIntro.text =
                                it.result.bookList!!.BOOK_INTRODUCTION
                        }
                        if (it.result.bookList!!.BOOK_INDEX == null){
                            binding.bookDetailTextViewIndex.text = "책 목차가 없습니다."
                        }
                        else{
                            binding.bookDetailTextViewIndex.text =
                                splitter(it.result.bookList!!.BOOK_INDEX)

                        }
                        tagAdapterSet(it.result.bookList.tagData)
                        binding.bookDetailImageViewBook.apply {
                            Glide.with(this)
                                .load(it.result.bookList!!.thumbnailImage) // 불러올 이미지 url
                                .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
                                .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
                                .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                                .into(binding.bookDetailImageViewBook) // 이미지를 넣을 뷰
                            //이미지 뷰 처리는 Glide 라이브러리 사용 예정
                        }
                        if(it.result.reviewList != null) {
                            reviewAdapterSet(it.result.reviewList!!)
                        }
                        successToCallFavorite(it.result.isFavorite!!)
                        successToCallGet(it.result.bookList.thumbnailImage,it.result.bookList.TITLE, it.result.bookList.AUTHOR, it.result.bookList.rating, it.result.bookList.TBID)
                    }

                }
            })

    }
}
interface callbackMoreAPI{
    fun callAPI()
}