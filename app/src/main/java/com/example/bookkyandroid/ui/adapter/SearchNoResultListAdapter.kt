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
import com.example.bookkyandroid.data.model.SearchResultDataModel

class SearchNoResultListAdapter (private val searchData : String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_PAGE_TYPE = 1

    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
    }
    override fun getItemViewType(position: Int): Int {
        // 게시물과 프로그레스바 아이템뷰를 구분할 기준이 필요하다.
        return VIEW_PAGE_TYPE

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_no_result,
            parent,
            false
        )
        return PagerViewHolder(view)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PagerViewHolder){
        }
    }

    override fun getItemCount(): Int = 1


}