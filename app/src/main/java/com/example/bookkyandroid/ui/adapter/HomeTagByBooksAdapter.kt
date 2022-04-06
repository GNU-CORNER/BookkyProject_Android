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
import com.example.bookkyandroid.data.model.HomeTagTestResponse

class HomeTagByBooksAdapter(private val title : ArrayList<HomeTagTestResponse>) : RecyclerView.Adapter<HomeTagByBooksAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tag: TextView =
            view.findViewById(R.id.recyclerView_item_home_tags_textView_title)
        val recyclerView : RecyclerView =
            view.findViewById(R.id.recyclerView_item_home_tags_recyclerView_books)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTagByBooksAdapter.PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_home_tags,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.tag.text = title[position].tag
    }

    override fun getItemCount(): Int = title.size

}