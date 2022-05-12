package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.ReviewDataModel

class BookDetailReviewAdapter(private val review: ArrayList<ReviewDataModel>) : RecyclerView.Adapter<BookDetailReviewAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView = view.findViewById(R.id.textView_book_detail_review_item_title)
        val expand : TextView = view.findViewById(R.id.textView_book_detail_more)
        val contents : TextView = view.findViewById(R.id.textView_book_detail_review_item_contents)
        val like : TextView = view.findViewById(R.id.textView_book_detail_likeCnt)
        val date: TextView = view.findViewById(R.id.textView_book_detail_createAt)
        val ratingBar : RatingBar = view.findViewById(R.id.ratingBar_book_detail_review_item)
        var isExpanded : Boolean = false
        val likeButton : TextView = view.findViewById(R.id.textView_book_detail_like)



        init {
            // Define click listener for the ViewHolder's View.


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_book_detail_review,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.name.text = review[position].nickname
        holder.contents.text = review[position].contents
        holder.like.text = review[position].likeCnt.toString()
        holder.ratingBar.rating =  review[position].rating.toFloat()
        holder.date.text = review[position].createAt

        holder.expand.setOnClickListener {
            if (holder.isExpanded){
                holder.contents.maxLines = 1000
                holder.isExpanded = false
            }
            else {
                holder.contents.maxLines = 2
                holder.isExpanded = true
            }

        }
    }

    override fun getItemCount(): Int = review.size

}