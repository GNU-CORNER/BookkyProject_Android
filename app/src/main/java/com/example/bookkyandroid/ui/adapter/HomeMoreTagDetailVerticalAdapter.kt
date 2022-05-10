package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.HomeBookDataModel

class HomeMoreTagDetailVerticalAdapter(private val testDatumDataModels: ArrayList<HomeBookDataModel?>) : RecyclerView.Adapter<HomeMoreTagDetailVerticalAdapter.PagerViewHolder>() {

    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView =
            view.findViewById(R.id.recyclerView_home_more_tag_detail)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMoreTagDetailVerticalAdapter.PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_more_tag_detail_vertical,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val books = testDatumDataModels!!

        holder.recyclerView.adapter = HomeMoreTagDetailHorizonalAdapter(books)
        val linearLayoutManager = LinearLayoutManager(holder.itemView.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        holder.recyclerView.layoutManager = linearLayoutManager

    }

    override fun getItemCount(): Int {
        return testDatumDataModels!!.size
    }

}
