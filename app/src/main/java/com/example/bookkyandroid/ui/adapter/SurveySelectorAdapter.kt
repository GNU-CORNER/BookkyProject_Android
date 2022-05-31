package com.example.bookkyandroid.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.SurveySelectorResponseDataModel
import com.example.bookkyandroid.ui.fragment.survey.SurveySelectorFragment

class SurveySelectorAdapter (private val itemData: SurveySelectorResponseDataModel, private val context: SurveySelectorFragment) : RecyclerView.Adapter<SurveySelectorAdapter.PagerViewHolder>() {
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.recyclerview_item_textview_surveySelector)
        val backgroundItem :FrameLayout = view.findViewById(R.id.framLayout_survey_selector_back_item)
        init {
            title.setTextColor(Color.BLACK)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_survey_selector,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        if (itemData.tag[position] != null) {
            holder.title.text = itemData.tag[position]!!.nameTag
            holder.title.maxLines = 2
            var isTouched = false
            holder.backgroundItem.setOnClickListener {
                if(isTouched == false){
                    holder.backgroundItem.setBackgroundResource(R.drawable.survey_selector_circle_selected)
                    holder.title.setTextColor(Color.WHITE)
                    context.putTagData(itemData.tag[position].TMID)
                    isTouched = true
                }
                else{
                    holder.backgroundItem.setBackgroundResource(R.drawable.survey_selector_circle)
                    holder.title.setTextColor(Color.BLACK)
                    context.delTagData(itemData.tag[position].TMID)
                    isTouched = false
                }
            }
        }
    }

    override fun getItemCount(): Int = itemData.tag.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
}