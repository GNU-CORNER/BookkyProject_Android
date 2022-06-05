package com.example.bookkyandroid.ui.fragment.review

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.databinding.FragmentReviewWriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding>(
    FragmentReviewWriteBinding::bind, R.layout.fragment_review_write) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val BID = arguments?.getInt("BID")
        val BookTitle = arguments?.getString("TITLE")
        val BookAuthor = arguments?.getString("AUTHOR")
        val Rating = arguments?.getFloat("RATING")
        val BookImage = arguments?.getString("thumbnail")
        val RID = arguments?.getInt("RID")
        val type = arguments?.getInt("type")
        Log.d("RID", RID!!.toString())

        binding.reviewWriteTextViewBookTitle.text = BookTitle.toString()
        binding.reviewWriteTextViewBookAuthor.text = BookAuthor.toString()
        binding.reviewWriteRatingBar.rating = Rating!!
        if (type == 0){
            val contents = arguments?.getString("contents")
            binding.reviewWriteEditTextTextMultiLine2.setText(contents)
        }
        Glide.with(this).load(BookImage)
            .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
            .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
            .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
            .into(binding.reviewWriteImageViewBookImage)
        binding.reviewWriteImageButtonCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.reviewWriteRatingBar.setOnRatingBarChangeListener{ _, rating, _ ->
            binding.reviewWriteRatingBar.rating = rating
        }
        CoroutineScope(Dispatchers.IO).launch {
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            binding.reviewWriteButtonWrite.setOnClickListener {
                if(RID!! > 0){
                    Log.d("RID", RID!!.toString())
                    reviewUpdate(bookkyService, RID!!, WriteReviewBodyDataModel(binding.reviewWriteEditTextTextMultiLine2.text.toString(),binding.reviewWriteRatingBar.rating))
                }
                else{
                    reviewWrite(bookkyService, BID!!, WriteReviewBodyDataModel(binding.reviewWriteEditTextTextMultiLine2.text.toString(),binding.reviewWriteRatingBar.rating))
                }
            }
        }
    }
    private fun successToCall(){
        findNavController().popBackStack()
    }
    private fun reviewWrite(bookkyService: BookkyService, BID : Int, body : WriteReviewBodyDataModel){
        bookkyService.writeReview(BID,body)
            .enqueue(object : Callback<BaseResponse<WriteReviewResponseDataModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<WriteReviewResponseDataModel>>,
                    t: Throwable
                ) {
                    Log.d("???ad?", t.toString())
                }

                override fun onResponse(
                    call: Call<BaseResponse<WriteReviewResponseDataModel>>,
                    response: Response<BaseResponse<WriteReviewResponseDataModel>>
                ) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        successToCall()
                        Log.d("api", "asd?")
                    }
                }
            })

    }
    private fun reviewUpdate(bookkyService: BookkyService, RID :Int, body:WriteReviewBodyDataModel){
        bookkyService.reviewUpdate(RID,body)
            .enqueue(object : Callback<BaseResponse<WriteReviewResponseDataModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<WriteReviewResponseDataModel>>,
                    t: Throwable
                ) {
                    Log.d("???ad?", t.toString())
                }

                override fun onResponse(
                    call: Call<BaseResponse<WriteReviewResponseDataModel>>,
                    response: Response<BaseResponse<WriteReviewResponseDataModel>>
                ) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        successToCall()
                        Log.d("api", "asd?")
                    }
                }
            })
    }

}