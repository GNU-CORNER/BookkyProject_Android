package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyWriting

class CommunityPostAdapter (private val myWriting : ArrayList<MyWriting>) : RecyclerView.Adapter<CommunityPostAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.community_post_item_title)
        val contents : TextView = view.findViewById(R.id.community_post_item_contents)
        val like : TextView = view.findViewById(R.id.community_post_item_textView_like)
        val comment: TextView = view.findViewById(R.id.community_post_item_textView_comment)


        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_post,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.title.text = myWriting[position].title
        holder.contents.text = myWriting[position].contents
        holder.like.text = myWriting[position].like.toString()
        holder.comment.text = myWriting[position].comment.toString()
    }

    override fun getItemCount(): Int = myWriting.size

}