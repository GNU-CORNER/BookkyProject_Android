package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyProfileReviewDataModel
import com.example.bookkyandroid.data.model.MyReview
import com.example.bookkyandroid.data.model.MyWriting

class MyInfoReviewAdapter (private val reviewData : ArrayList<MyProfileReviewDataModel>) : RecyclerView.Adapter<MyInfoReviewAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.my_info_my_review_item_title)
        val contents : TextView = view.findViewById(R.id.my_info_my_review_item_textView_contents)
        val like : TextView = view.findViewById(R.id.my_info_my_review_item_textView_like)
        val writer: TextView = view.findViewById(R.id.my_info_my_review_item_textView_writer)
        val ratingBar : RatingBar = view.findViewById(R.id.my_info_my_review_item_ratingBar)
        val ratingNum : TextView = view.findViewById(R.id.my_info_my_review_item_textView_ratingNum)
        val image : ImageView = view.findViewById(R.id.my_info_my_review_item_imageView)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_my_info_my_review,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.apply {
            Glide.with(itemView)
                .load(reviewData[position]!!.thumbnail) // 불러올 이미지 url
                .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
                .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .into(holder.image) // 이미지를 넣을 뷰
            //이미지 뷰 처리는 Glide 라이브러리 사용 예정
        }
        holder.title.text = reviewData[position].bookTitle!!
        holder.contents.text = reviewData[position].contents!!
        holder.like.text = reviewData[position].likeCnt.toString()
        holder.ratingNum.text = reviewData[position].rating.toString()
        holder.ratingBar.rating =  reviewData[position].rating!!.toFloat()
        holder.writer.text = reviewData[position].nickname!!
    }

    override fun getItemCount(): Int = reviewData.size

}