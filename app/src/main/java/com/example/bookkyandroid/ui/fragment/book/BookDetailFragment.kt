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
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.RetrofitManager
import com.example.bookkyandroid.data.model.*
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


class BookDetailFragment: BaseFragment<FragmentBookDetailBinding>(FragmentBookDetailBinding::bind, R.layout.fragment_book_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookID = arguments?.getInt("BID")
        showLoadingDialog(requireContext())
        var expand1 = false
        var expand2 = false
        CoroutineScope(Dispatchers.IO).launch {
            val bookkyService = RetrofitManager.getInstance().bookkyService
            val access_token = ApplicationClass.getInstance().getDataStore().accessToken.first()
            getBookDetail(bookkyService,bookID!!, access_token)
        }
        binding.bookDetailImageBtnLike.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val bookkyService = RetrofitManager.getInstance().bookkyService
                val access_token = ApplicationClass.getInstance().getDataStore().accessToken.first()
                postFavorite(bookkyService, bookID!!,access_token)
            }
        }
        binding.bookDetailTextViewExpand1.setOnClickListener {
            if(!expand1){
                binding.bookDetailTextViewIntro.maxLines = 9999
                expand1 = true
            }
            else{
                binding.bookDetailTextViewIntro.maxLines = 4
                expand1 = false
            }
        }
        binding.bookDetailTextViewIndex.setOnClickListener {

            binding.bookDetailTextViewIndex.maxLines = 4
            expand2 = false
        }
        binding.bookDetailTextViewIntro.setOnClickListener {
            binding.bookDetailTextViewIntro.maxLines = 4
            expand1 = false
        }
        binding.bookDetailTextViewExpand2.setOnClickListener {
            if(!expand2){
                binding.bookDetailTextViewIndex.maxLines = 9999
                expand2 = true
            }
            else{
                binding.bookDetailTextViewIndex.maxLines = 4
                expand2 = false
            }
        }

    }
    private fun successToCallGet(image : String, TITLE : String, AUTHOR : String, RATING : Float, BID:Int){
        sleep(500)
        dismissLoadingDialog()
        binding.bookyDetailTextViewWriteReview.setOnClickListener {
            val bundle = bundleOf("thumbnail" to image)
            bundle.putString("TITLE", TITLE)
            bundle.putString("AUTHOR", AUTHOR)
            bundle.putFloat("RATING", RATING)
            bundle.putInt("BID", BID)
            findNavController().navigate(R.id.action_bookDetailFragment_to_reviewWriteFragment, bundle)
        }
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
            reviewData = review
        }
        binding.bookDetailRecyclerViewReviews.adapter = BookDetailReviewAdapter(reviewData)
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

    private fun postFavorite(bookkyService: BookkyService, pk:Int, access_token: String){
        bookkyService.favoriteBook(access_token,pk)
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
    private fun getBookDetail(bookkyService: BookkyService, pk:Int, access_token :String){

        bookkyService.getBookDetail(pk, access_token)
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

                        reviewAdapterSet(it.result.reviewList!!)
                        Log.d("favorite?",it.result.isFavorite.toString())
                        successToCallFavorite(it.result.isFavorite!!)
                        successToCallGet(it.result.bookList.thumbnailImage,it.result.bookList.TITLE, it.result.bookList.AUTHOR, it.result.bookList.rating, it.result.bookList.TBID)
                    }

                }
            })
    }
}