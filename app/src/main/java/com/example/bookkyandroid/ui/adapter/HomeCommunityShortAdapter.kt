package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.HomeCommunityDataModel

class HomeCommunityShortAdapter(private val communityData : ArrayList<HomeCommunityDataModel>) : RecyclerView.Adapter<HomeCommunityShortAdapter.PagerViewHolder>() {
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
        if (communityData[position].communityType == 0){
            holder.communityName.text = "자유게시판"
        }
        else if (communityData[position].communityType == 1){
            holder.communityName.text = "QnA게시판"
        }
        else if (communityData[position].communityType == 2){
            holder.communityName.text = "장터게시판"
        }
        else{
            holder.communityName.text = "핫게시판"
        }
        holder.communityPostTitle.text = communityData[position].title
    }

    override fun getItemCount(): Int = communityData.size

}