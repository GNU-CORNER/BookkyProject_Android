package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.data.model.SuggestionContents

class SuggestionAdapter (private val contents : ArrayList<SuggestionContents>) : RecyclerView.Adapter<SuggestionAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val text1 : TextView = view.findViewById(R.id.suggestion_item_textView_line1)
        val text2 : TextView = view.findViewById(R.id.suggestion_item_textView_line2)
        val text3 : TextView = view.findViewById(R.id.suggestion_item_textView_line3)
        val text4 : TextView = view.findViewById(R.id.suggestion_item_textView_start_bookky)
        val btnLayout : LinearLayout = view.findViewById(R.id.suggestion_item_linearLayout2)
        val newTag : ImageView = view.findViewById(R.id.suggestion_item_imageView_newTag)
        val light : ImageView = view.findViewById(R.id.suggestion_item_imageView_light)
        val backgroundImage : ImageView = view.findViewById(R.id.suggestion_item_imageView_background)
        val backgroundLayout : ConstraintLayout = view.findViewById(R.id.suggestion_item_constraintLayout)


        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_suggestion,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.text1.text = contents[position].text1
        holder.text2.text = contents[position].text2
        holder.text3.text = contents[position].text3
        holder.text4.text = contents[position].text4

        Glide.with(holder.itemView.context)
            .load(contents[position].backgroundImage)
            .into(holder.backgroundImage)


        if (contents[position].isNew) {
            holder.newTag.visibility = VISIBLE
        }

        if (contents[position].isLast){
            holder.btnLayout.visibility = INVISIBLE
            holder.backgroundLayout.setBackgroundResource(R.drawable.suggestion_item_background2)
            holder.light.visibility = VISIBLE
            holder.text1.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.gray3))
            holder.text2.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.gray3))
            holder.text3.visibility = INVISIBLE
        }


    }

    override fun getItemCount(): Int = contents.size

}