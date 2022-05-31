package com.example.bookkyandroid.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.HomeBookListDataModel


class HomeMoreTagDetailAdapter(private val bookData: HomeBookListDataModel) : RecyclerView.Adapter<HomeMoreTagDetailAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.more_tag_detail_books_item_textView_title)
        val image : ImageView = view.findViewById(R.id.more_tag_detail_books_tag_books_item_imageView)

        init {
            title.setTextColor(Color.BLACK)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_more_tag_detail_horizonal,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        if (bookData.data[position] != null) {
            holder.title.text = bookData.data[position]!!.TITLE
            holder.apply {
                Glide.with(itemView)
                    .load(bookData.data[position]!!.thumbnailImage) // 불러올 이미지 url
                    .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
                    .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
                    .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                    .into(holder.image) // 이미지를 넣을 뷰
                //이미지 뷰 처리는 Glide 라이브러리 사용 예정
            }
            holder.itemView.setOnClickListener {
                val bundle = bundleOf("BID" to bookData.data[position]!!.TBID)
                it.findNavController().navigate(R.id.action_global_bookDetailFragment, bundle)
            }
        }
    }
    override fun getItemCount(): Int = bookData.data.size
}