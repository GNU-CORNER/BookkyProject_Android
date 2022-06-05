package com.example.bookkyandroid.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.CommunityAllTypePostDataModel
import com.example.bookkyandroid.data.model.CommunityDetailCommentDataModel
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.ui.fragment.community.CommunityDetailFragment
import com.example.bookkyandroid.ui.fragment.community.CommunityFragmentDirections

class CommunityPostAdapter(private val myWriting: ArrayList<CommunityAllTypePostDataModel>, val communityType: String) : RecyclerView.Adapter<CommunityPostAdapter.PagerViewHolder>() {
    private val DEFAULT_TYPE = 0
    private val QNA_TYPE = 1
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title : TextView = view.findViewById(R.id.community_post_item_title)
        val contents : TextView = view.findViewById(R.id.community_post_item_contents)
        val like : TextView = view.findViewById(R.id.community_post_item_textView_like)
        val comment: TextView = view.findViewById(R.id.community_post_item_textView_comment)
        val layout : LinearLayout = view.findViewById(R.id.community_linearLayout_recyclerview_item)
        val reply : TextView? = view.findViewById(R.id.community_post_item_replyCnt)

        init {
            // Define click listener for the ViewHolder's View.

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

        return when (viewType) {
            DEFAULT_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_post,
                    parent,
                    false
                )
                CommunityPostAdapter.PagerViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_qna_post,
                    parent,
                    false
                )
                CommunityPostAdapter.PagerViewHolder(view)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        if( communityType == "3" || communityType == "4") {
            return when(myWriting[position].communityType.toString() ){
                "2" -> QNA_TYPE
                else -> DEFAULT_TYPE
            }
        }
        return when(communityType){
            "2" -> QNA_TYPE
            else -> DEFAULT_TYPE
        }
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {


        holder.title.text = myWriting[position].title
        holder.contents.text = myWriting[position].contents
        holder.like.text = myWriting[position].likeCnt.toString()
        holder.comment.text = myWriting[position].commentCnt.toString()
        if(communityType == "2" ||  myWriting[position].communityType.toString() == "2") {
            holder.reply?.text = myWriting[position].replyCnt.toString()+"\n답글"
        }
        holder.layout.setOnClickListener{

            val bundle = bundleOf(
                    "PID" to myWriting[position].PID.toString(),
                    "communityType" to myWriting[position].communityType.toString()
                )
                it.findNavController()
                    .navigate(R.id.action_communityFragment_to_communityDetailFragment, bundle)


        }
    }


    override fun getItemCount(): Int = myWriting.size

}