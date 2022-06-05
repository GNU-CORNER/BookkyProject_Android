package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyProfileReviewDataModel

class MyInfoReviewAdapter (private val reviewData : ArrayList<MyProfileReviewDataModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_NO_OBJECT_TYPE = 0
    private val VIEW_PAGE_TYPE = 1
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.my_info_my_review_item_title)
        val contents : TextView = view.findViewById(R.id.my_info_my_review_item_textView_contents)
        val like : TextView = view.findViewById(R.id.my_info_my_review_item_textView_like)
        val writer: TextView = view.findViewById(R.id.my_info_my_review_item_textView_writer)
        val ratingBar : RatingBar = view.findViewById(R.id.my_info_my_review_item_ratingBar)
        val image : ImageView = view.findViewById(R.id.my_info_my_review_item_imageView)
        val publishDate : TextView = view.findViewById(R.id.my_review_item_textView_publish_date)

        init {

        }
    }
    class NoObjectViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val noObjectTitle : TextView = view.findViewById(R.id.recyclerview_textView_no_object_title)
    }

    override fun getItemViewType(position: Int): Int {
        return when(reviewData[position].RID){
            0 -> VIEW_NO_OBJECT_TYPE
            else -> VIEW_PAGE_TYPE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            VIEW_PAGE_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_my_info_my_review,
                    parent,
                    false
                )
                PagerViewHolder(view)
            }
            else ->{
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_no_object,
                    parent,
                    false
                )
                NoObjectViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PagerViewHolder){
            holder.apply {
                Glide.with(itemView)
                    .load(reviewData[position]!!.thumbnail) // 불러올 이미지 url
                    .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
                    .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
                    .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                    .into(holder.image) // 이미지를 넣을 뷰
                //이미지 뷰 처리는 Glide 라이브러리 사용 예정
            }
            if (reviewData[position].bookTitle.toString().length >10){
                holder.title.text = reviewData[position].bookTitle.toString().substring(0,10) + "..."
            }
            else{
                holder.title.text = reviewData[position].bookTitle.toString()
            }
            holder.title.text = reviewData[position].bookTitle.toString()
            holder.contents.text = reviewData[position].contents.toString()
            holder.like.text = reviewData[position].likeCnt.toString()
            holder.publishDate.text = reviewData[position].createAt.toString()
            holder.ratingBar.rating =  reviewData[position].rating!!.toFloat()
            holder.writer.text = reviewData[position].AUTHOR.toString()
            holder.itemView.setOnClickListener {
                val bundle = bundleOf("BID" to reviewData[position]!!.TBID)
                it.findNavController().navigate(R.id.action_global_bookDetailFragment, bundle)
            }
        }
        else if (holder is NoObjectViewHolder){
            holder.noObjectTitle.text = "작성한 리뷰가 없어요."
        }

    }

    override fun getItemCount(): Int = reviewData.size

}