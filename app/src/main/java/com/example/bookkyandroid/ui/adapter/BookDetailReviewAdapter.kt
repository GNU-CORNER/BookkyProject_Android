package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.BookDetailTestModel
import com.example.bookkyandroid.data.model.MyReview
import com.example.bookkyandroid.data.model.MyWriting
import org.w3c.dom.Text

class BookDetailReviewAdapter(private val review: ArrayList<BookDetailTestModel>) : RecyclerView.Adapter<BookDetailReviewAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView = view.findViewById(R.id.recyclerView_item_book_detail_textView_name)
        val expand : TextView = view.findViewById(R.id.recyclerView_item_book_detail_textView_expand)
        val contents : TextView = view.findViewById(R.id.recyclerView_item_book_detail_textView_contents)
        val like : TextView = view.findViewById(R.id.recyclerView_item_book_detail_textView_like)
        val date: TextView = view.findViewById(R.id.recyclerView_item_book_detail_textView_date)
        val image : ImageView = view.findViewById(R.id.recyclerView_item_book_detail_imageView)
        val ratingBar : RatingBar = view.findViewById(R.id.recyclerView_item_book_detail_ratingBar)
        var isExpanded : Boolean = false



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
        holder.name.text = review[position].name
        holder.contents.text = review[position].contents
        holder.like.text = review[position].like.toString()
        holder.ratingBar.rating =  review[position].rating.toFloat()
        holder.date.text = review[position].date
        holder.isExpanded = review[position].isExpanded

        holder.expand.setOnClickListener {
            if (holder.isExpanded){
                holder.contents.maxLines = 1000
                holder.isExpanded = false
                review[position].isExpanded = holder.isExpanded
            }
            else {
                holder.contents.maxLines = 2
                holder.isExpanded = true
                review[position].isExpanded = holder.isExpanded
            }

        }
    }

    override fun getItemCount(): Int = review.size

}