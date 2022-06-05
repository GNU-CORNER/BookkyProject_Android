package com.example.bookkyandroid.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.databinding.BottomsheetMoreConfigurationBinding
import com.example.bookkyandroid.ui.dialog.getActionItem

class BottomSheetMoreConfigurationAdapter(private val titleData : ArrayList<Int>, private val RID :Int, listener: getActionItem) : RecyclerView.Adapter<BottomSheetMoreConfigurationAdapter.PagerViewHolder>() {
    private val DELETE_ITEM = 0
    private val EDIT_ITEM = 1
    private val REPORT_ITEM = 2
    private val itemListener = listener
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.recyclerview_item_more_configuration_title)
        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_more_configuration,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        var flag : Int = 0
        when(titleData[position]){
            DELETE_ITEM ->{
                holder.title.text = "삭제하기"
                holder.title.setTextColor(Color.parseColor("#F2B1B1"))
                flag =DELETE_ITEM
            }
            EDIT_ITEM ->{
                holder.title.text = "수정하기"
                holder.title.setTextColor(Color.parseColor("#85A7FF"))
                flag =EDIT_ITEM
            }
            REPORT_ITEM ->{
                holder.title.text = "신고하기"
                holder.title.setTextColor(Color.parseColor("#F2B1B1"))
                flag = REPORT_ITEM
            }
        }
        holder.itemView.setOnClickListener{
            itemListener.getAction(flag)
        }
    }

    override fun getItemCount(): Int = titleData.size
}