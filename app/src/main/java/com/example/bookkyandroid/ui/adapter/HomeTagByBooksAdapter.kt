package com.example.bookkyandroid.ui.adapter

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class HomeTagByBooksAdapter(private val data : HomeBookListDataModel) : RecyclerView.Adapter<HomeTagByBooksAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_textView_title)
        val image : ImageView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_imageView)
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTagByBooksAdapter.PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_interested_books,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.title.text = data.data[position]!!.TITLE
        holder.apply {
            Glide.with(itemView)
                .load(data.data[position]!!.thumbnailImage) // 불러올 이미지 url
                .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
                .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .into(holder.image) // 이미지를 넣을 뷰
            //이미지 뷰 처리는 Glide 라이브러리 사용 예정
        }
        if (position <= data.data.size) {
            val endPosition = if (position + 6 > data.data.size) {
                data.data.size
            } else {
                position + 6
            }
            data.data.subList(position, endPosition ).map { it!!.thumbnailImage }.forEach {
                preLoad(holder.itemView, it.toString())
            }
        }
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("BID" to data.data[position]!!.TBID)
            it.findNavController().navigate(R.id.action_global_bookDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = data.data.size
    fun preLoad(view: View, url : String) {
        Glide.with(view).load(url)
            .preload()
    }
}