package com.example.bookkyandroid.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.HomeBookDataModel

class MyInfoInterestedBooksAdapter(private val bookData : ArrayList<HomeBookDataModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_NO_OBJECT_TYPE = 0
    private val VIEW_PAGE_TYPE = 1
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_textView_title)
        val image : ImageView = view.findViewById(R.id.myInfo_viewPager_interested_books_item_imageView)

        init {
            title.setTextColor(Color.BLACK)
        }
    }
    class NoObjectViewHolder(view : View) : RecyclerView.ViewHolder(view){

    }

    override fun getItemViewType(position: Int): Int {
        return when(bookData[position].TBID){
            0 -> VIEW_NO_OBJECT_TYPE
            else -> VIEW_PAGE_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_NO_OBJECT_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_no_object,
                    parent,
                    false
                )
                 NoObjectViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_interested_books,
                    parent,
                    false
                )
                PagerViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PagerViewHolder){
            holder.title.text = bookData[position].TITLE!!
            holder.apply {
                Glide.with(itemView)
                    .load(bookData[position]!!.thumbnailImage!!) // ????????? ????????? url
                    .placeholder(R.drawable.test_book) // ????????? ?????? ???????????? ??? ????????? ?????????
                    .error(R.drawable.test_book) // ?????? ?????? ?????? ??? ????????? ?????????
                    .fallback(R.drawable.test_book) // ????????? url ??? ????????????(null ???) ?????? ????????? ?????????
                    .into(holder.image) // ???????????? ?????? ???
                //????????? ??? ????????? Glide ??????????????? ?????? ??????
            }
            holder.itemView.setOnClickListener {
                val bundle = bundleOf("BID" to bookData[position]!!.TBID)
                it.findNavController().navigate(R.id.action_global_bookDetailFragment, bundle)
            }
        }
        else{

        }
    }

    override fun getItemCount(): Int = bookData.size

}