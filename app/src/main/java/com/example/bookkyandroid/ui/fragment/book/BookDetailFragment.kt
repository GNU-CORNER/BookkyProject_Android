package com.example.bookkyandroid.ui.fragment.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailFragment: BaseFragment<FragmentBookDetailBinding>(FragmentBookDetailBinding::bind, R.layout.fragment_book_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookkyService = RetrofitManager.getInstance().bookkyService
        val bookID = arguments?.getInt("BID")
        getBookDetail(bookkyService,bookID!!)
    }
    private fun tagAdapterSet(tags: ArrayList<TagDataResponseDataModel>) {
        binding.bookDetailRecyclerViewTags.adapter = MyInfoInterestedAreaAdapter(tags)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.bookDetailRecyclerViewTags.layoutManager = linearLayoutManager
    }


    private fun reviewAdapterSet(review: ArrayList<ReviewDataModel>) {
        binding.bookDetailRecyclerViewReviews.adapter = BookDetailReviewAdapter(review)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.bookDetailRecyclerViewReviews.layoutManager = linearLayoutManager
    }

    private fun getBookDetail(bookkyService: BookkyService, pk:Int){
        val access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzX3Rva2VuIiwiZXhwIjoxNjUyMzQ5MTg0LCJVSUQiOjYxfQ.kGbKUtn3SLcwisEGAV5yphfDlMkz05F7ZoU1Dn7GzPo"
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
                        binding.bookDetailTextViewIntro.text =
                            it.result.bookList!!.BOOK_INTRODUCTION
                        binding.bookDetailTextViewIndex.text =
                            it.result.bookList!!.BOOK_INDEX
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
                    }

                }
            })
    }
}