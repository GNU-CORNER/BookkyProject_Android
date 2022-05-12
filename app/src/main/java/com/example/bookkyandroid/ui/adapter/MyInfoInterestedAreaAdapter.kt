package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.TagDataResponseDataModel

class MyInfoInterestedAreaAdapter(private val tags : ArrayList<TagDataResponseDataModel>) : RecyclerView.Adapter<MyInfoInterestedAreaAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tagName : TextView = view.findViewById(R.id.myInfo_recyclerview_interested_area_item_textView_tag)
        val itemFrame : CardView = view.findViewById(R.id.cardView_myinfo_tag_item_frame)
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_interested_area,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.tagName.text = tags[position].tag
        holder.itemFrame.setOnClickListener{
            val bundle = bundleOf("TID" to tags[position]!!.TID)
            it.findNavController().navigate(R.id.homeMoreTagDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = tags.size

}