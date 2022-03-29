package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyReview
import com.example.bookkyandroid.data.model.MyWriting

class MyInfoReviewAdapter (private val myReview : ArrayList<MyReview>) : RecyclerView.Adapter<MyInfoReviewAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.my_info_my_review_item_title)
        val contents : TextView = view.findViewById(R.id.my_info_my_review_item_textView_contents)
        val like : TextView = view.findViewById(R.id.my_info_my_review_item_textView_like)
        val writer: TextView = view.findViewById(R.id.my_info_my_review_item_textView_writer)
        val ratingBar : RatingBar = view.findViewById(R.id.my_info_my_review_item_ratingBar)
        val ratingNum : TextView = view.findViewById(R.id.my_info_my_review_item_textView_ratingNum)


        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_my_info_my_review,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.title.text = myReview[position].title
        holder.contents.text = myReview[position].contents
        holder.like.text = myReview[position].like.toString()
        holder.ratingNum.text = myReview[position].rating.toString()
        holder.ratingBar.rating =  myReview[position].rating.toFloat()
        holder.writer.text = myReview[position].writer
    }

    override fun getItemCount(): Int = myReview.size

}