package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.SearchResultResponseDataModel

class SearchResultListAdapter (private val searchData : ArrayList<SearchResultResponseDataModel>) : RecyclerView.Adapter<SearchResultListAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle : TextView = view.findViewById(R.id.searchResult_textView_bookTitle)
        val bookAuthor : TextView = view.findViewById(R.id.searchResult_textView_bookAuthor)
        val ratingBar : RatingBar = view.findViewById(R.id.searchResult_item_ratingBar)
        val tagRecyclerView : RecyclerView = view.findViewById(R.id.searchResult_recyclerView)
        val bookImage : ImageView = view.findViewById(R.id.searchResult_item_imageView)
        val ratingNum : TextView = view.findViewById(R.id.searchResult_item_textView_ratingNum)
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_searchresult_item,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bookAuthor.text = searchData[position].AUTHOR
        holder.bookTitle.text = searchData[position].TITLE
        holder.ratingNum.text = searchData[position].RATING.toString()
        holder.apply {
            Glide.with(itemView)
                .load(searchData[position]!!.thumbnailImage) // 불러올 이미지 url
                .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
                .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .into(holder.bookImage) // 이미지를 넣을 뷰
            //이미지 뷰 처리는 Glide 라이브러리 사용 예정
        }
        holder.ratingBar.rating = searchData[position].RATING

        holder.tagRecyclerView.adapter = SearchResultTagListAdapter(searchData[position].tagData)
        val linearLayoutManager = LinearLayoutManager(holder.itemView.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        holder.tagRecyclerView.layoutManager = linearLayoutManager
    }

    override fun getItemCount(): Int = searchData.size

}