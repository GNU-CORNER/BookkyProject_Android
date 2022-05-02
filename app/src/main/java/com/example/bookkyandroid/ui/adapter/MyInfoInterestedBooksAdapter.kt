package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R

class MyInfoInterestedBooksAdapter(private val title : ArrayList<String>, val navControl: NavController) : RecyclerView.Adapter<MyInfoInterestedBooksAdapter.PagerViewHolder>() {
    inner class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_textView_title)
        val image : ImageView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_imageView)

        init {
            view.setOnClickListener {
                //특정 데이터도 같이 넘겨줘야 함 (API 확인 후 수정)
                navControl.navigate(R.id.action_global_bookDetailFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_interested_books,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.title.text = title[position]
    }

    override fun getItemCount(): Int = title.size

}