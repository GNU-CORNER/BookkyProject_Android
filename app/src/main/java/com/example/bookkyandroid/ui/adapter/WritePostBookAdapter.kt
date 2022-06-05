package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.SearchResultDataModel
import com.example.bookkyandroid.data.model.WriteBookSearchDataModel
import com.example.bookkyandroid.databinding.FragmentCommunityPostWriteBinding

class WritePostBookAdapter (private val itemData: WriteBookSearchDataModel,private val binding: FragmentCommunityPostWriteBinding) : RecyclerView.Adapter<WritePostBookAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.findViewById(R.id.write_post_imageView_book_iamge)
        val cancelButton : ImageButton = view.findViewById(R.id.write_post_book_imageButton_cancel)
        val title : TextView = view.findViewById(R.id.write_post_book_title)
        val author : TextView = view.findViewById(R.id.write_post_book_author)
        val tagString : TextView = view.findViewById(R.id.write_post_book_tag_string)
        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_write_post_book,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        var text : String = ""
        holder.apply {
           Glide.with(imageView)
               .load(itemData.thumbnailImage)
               .override(80, 80)
               .diskCacheStrategy(DiskCacheStrategy.NONE)
               .skipMemoryCache(true)
               .into(holder.imageView)
        }
        holder.title.text = itemData.TITLE
        holder.author.text = itemData.AUTHOR + " / " + itemData.PUBLISHER
        for(i in itemData.tagData){
            text += "#"+i.tag +" "
        }
        holder.tagString.text = text
        holder.cancelButton.setOnClickListener {
            binding.communityRecyclerViewPostBook.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int {
        return position
    }
}