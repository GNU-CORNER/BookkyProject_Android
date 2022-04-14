package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.HomeCommunityDataModel

class HomeCommunityShortAdapter(private val title : ArrayList<HomeCommunityDataModel>) : RecyclerView.Adapter<HomeCommunityShortAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val communityName: TextView =
            view.findViewById(R.id.home_textView_recyclerView_communityName)
        val communityPostTitle: TextView =
            view.findViewById(R.id.home_textView_recyclerView_communityPostTitle)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCommunityShortAdapter.PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_postlist,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.communityName.text = title[position].communityName
        holder.communityPostTitle.text = title[position].postTitle
    }

    override fun getItemCount(): Int = title.size

}