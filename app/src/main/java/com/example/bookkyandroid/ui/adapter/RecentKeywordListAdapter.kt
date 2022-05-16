package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyProfilePostDataModel
import com.example.bookkyandroid.data.model.MyWriting

class RecentKeywordListAdapter (private val keywordList : ArrayList<String>) : RecyclerView.Adapter<RecentKeywordListAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val keyword : TextView = view.findViewById(R.id.textView_item_keyword)

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_recent_keyword,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.keyword.text = keywordList[position]

    }

    override fun getItemCount(): Int = keywordList.size

}