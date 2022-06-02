package com.example.bookkyandroid.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.databinding.FragmentCommunityPostWriteBinding

class WritePostImageAdapter (private val imageData: ArrayList<Uri>,private val binding:FragmentCommunityPostWriteBinding) : RecyclerView.Adapter<WritePostImageAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.findViewById(R.id.write_post_imageView_image)
        val cancelButton : ImageButton = view.findViewById(R.id.write_post_button_item_imageButton2)
        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_post_images,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.imageView.setImageURI(imageData[position])
        holder.cancelButton.setOnClickListener {
            imageData.removeAt(position)
            notifyDataSetChanged()
            if (imageData.size == 0){
                binding.communityRecyclerViewPostImages.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int = imageData.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
}