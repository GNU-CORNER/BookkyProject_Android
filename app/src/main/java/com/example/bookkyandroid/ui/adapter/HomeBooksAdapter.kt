package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.HomeBookTestResponse

class HomeBooksAdapter(private val title : ArrayList<HomeBookTestResponse>) : RecyclerView.Adapter<HomeBooksAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_textView_title)
        val image : ImageView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_imageView)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_interested_books,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.title.text = title[position].title

        //이미지 뷰 처리는 Glide 라이브러리 사용 예정
    }

    override fun getItemCount(): Int = title.size

}