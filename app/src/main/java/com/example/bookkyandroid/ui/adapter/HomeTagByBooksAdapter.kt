package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.BookResult
import com.example.bookkyandroid.data.model.HomeTagBookDataModel

class HomeTagByBooksAdapter(private val title : List<BookResult>) : RecyclerView.Adapter<HomeTagByBooksAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val bookTitle: TextView =
            view.findViewById(R.id.home_viewPager_tag_books_item_textView_title)
        val image: ImageView =
            view.findViewById(R.id.home_viewPager_tag_books_item_imageView)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTagByBooksAdapter.PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_tag_books,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bookTitle.text = title[position].TITLE
    }

    override fun getItemCount(): Int = title.size

}