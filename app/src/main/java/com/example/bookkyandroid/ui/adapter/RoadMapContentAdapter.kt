package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.ui.fragment.suggestion.RoadMapDetailFragment


class RoadMapContentAdapter (private val data : ArrayList<String>, private val navController: NavController) : RecyclerView.Adapter<RoadMapContentAdapter.PagerViewHolder>() {
    inner class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val content : TextView = view.findViewById(R.id.road_map_content_item_textView)
        val btn : ImageButton = view.findViewById(R.id.road_map_content_item_imageButton)

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener {
                navController.navigate(R.id.action_global_roadMapDetailFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_road_map_content,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.content.text = data[position]

    }

    override fun getItemCount(): Int = data.size

}