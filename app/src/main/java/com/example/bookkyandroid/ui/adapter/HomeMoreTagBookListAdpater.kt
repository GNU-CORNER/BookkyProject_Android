package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.HomeBookListDataModel
import com.example.bookkyandroid.ui.fragment.home.HomeMoreTagDetailFragment

class HomeMoreTagBookListAdpater (private val testDatumDataModels : ArrayList<HomeBookListDataModel?>) : RecyclerView.Adapter<HomeMoreTagBookListAdpater.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tag: TextView =
            view.findViewById(R.id.recyclerView_item_home_tags_textView_title)
        val recyclerView : RecyclerView =
            view.findViewById(R.id.recyclerView_item_home_tags_recyclerView_books)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMoreTagBookListAdpater.PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_home_tags,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.tag.text = testDatumDataModels[position]!!.tag

        val books = testDatumDataModels[position]!!.data

        holder.recyclerView.adapter = HomeMoreTagBookAdapter(books)
        val linearLayoutManager = LinearLayoutManager(holder.itemView.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        holder.recyclerView.layoutManager = linearLayoutManager

        holder.tag.setOnClickListener {
            val bundle = bundleOf("TID" to testDatumDataModels[position]!!.TID)
            it.findNavController().navigate(R.id.homeMoreTagDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return testDatumDataModels.size
    }

}