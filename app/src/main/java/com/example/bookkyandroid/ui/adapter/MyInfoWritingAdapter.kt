package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyProfilePostDataModel
import com.example.bookkyandroid.data.model.MyWriting

class MyInfoWritingAdapter (private val postData : ArrayList<MyProfilePostDataModel>) : RecyclerView.Adapter<MyInfoWritingAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.my_info_my_writing_item_title)
        val contents : TextView = view.findViewById(R.id.my_info_my_writing_item_contents)
        val like : TextView = view.findViewById(R.id.my_info_my_writing_item_textView_like)
        val comment: TextView = view.findViewById(R.id.my_info_my_writing_item_textView_comment)


        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_my_info_my_writing,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.title.text = postData[position].title!!
        holder.contents.text = postData[position].contents!!
        holder.like.text = postData[position].likeCnt.toString()
        holder.comment.text = postData[position].commentCnt.toString()
    }

    override fun getItemCount(): Int = postData.size

}