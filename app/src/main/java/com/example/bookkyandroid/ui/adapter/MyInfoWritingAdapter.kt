package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.MyProfilePostDataModel
import com.example.bookkyandroid.data.model.MyWriting

class MyInfoWritingAdapter (private val postData : ArrayList<MyProfilePostDataModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_NO_OBJECT_TYPE = 0
    private val VIEW_PAGE_TYPE = 1
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.my_info_my_writing_item_title)
        val contents : TextView = view.findViewById(R.id.my_info_my_writing_item_contents)
        val like : TextView = view.findViewById(R.id.my_info_my_writing_item_textView_like)
        val comment: TextView = view.findViewById(R.id.my_info_my_writing_item_textView_comment)


        init {
            // Define click listener for the ViewHolder's View.
        }
    }
    class NoObjectViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val noObjectTitle : TextView = view.findViewById(R.id.recyclerview_textView_no_object_title)
    }

    override fun getItemViewType(position: Int): Int {
        return when(postData[position].PID){
            0 -> VIEW_NO_OBJECT_TYPE
            else -> VIEW_PAGE_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_PAGE_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_my_info_my_writing,
                    parent,
                    false
                )
                PagerViewHolder(view)
            }
            else ->{
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_no_object,
                    parent,
                    false
                )
                NoObjectViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PagerViewHolder) {
            holder.title.text = postData[position].title!!
            holder.contents.text = postData[position].contents!!
            holder.like.text = postData[position].likeCnt.toString()
            holder.comment.text = postData[position].commentCnt.toString()
        }
        else if (holder is NoObjectViewHolder){
            holder.noObjectTitle.text = "작성한 글이 없어요."
        }

    }

    override fun getItemCount(): Int = postData.size

}