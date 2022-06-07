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
import com.example.bookkyandroid.data.model.CommunityDetailCommentDataModel
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.ui.fragment.community.CommunityDetailFragment
import com.example.bookkyandroid.ui.fragment.community.CommunityFragmentDirections

class CommunityDetailCommentAdapter(private val comment: ArrayList<CommunityDetailCommentDataModel>, val communityType: String) : RecyclerView.Adapter<CommunityDetailCommentAdapter.PagerViewHolder>() {
    private val COMMENT_TYPE = 0
    private val REPLY_COMMENT_TYPE = 1

    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nickname : TextView = view.findViewById(R.id.community_textView_comments_nickname)
        val contents : TextView = view.findViewById(R.id.community_textView_comments_contents)
        val updateAt : TextView = view.findViewById(R.id.community_textView_comments_updateAt)
        val like: TextView = view.findViewById(R.id.community_textView_comments_likeCnt)
//        val layout : LinearLayout = view.findViewById(R.id.community_linearLayout_recyclerview_item)
//       가로 메뉴 버튼!
        init {
            // Define click listener for the ViewHolder's View.

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

        return when (viewType) {
            COMMENT_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_community_comments,
                    parent,
                    false
                )
                PagerViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_community_replycomments,
                    parent,
                    false
                )
                PagerViewHolder(view)
            }


        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(comment[position].reply){
            0 -> COMMENT_TYPE
            else -> REPLY_COMMENT_TYPE
        }
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Log.d("Test","뷰홀더")
        Log.d("Test",comment.size.toString())
        holder.nickname.text = comment[position].nickname
        holder.contents.text = comment[position].comment
        holder.like.text = "공감("+ comment[position].like?.size.toString()+")"
        holder.updateAt.text = comment[position].updateAt
//        holder.layout.setOnClickListener{
//            val bundle = bundleOf(
//                "PID" to myWriting[position].PID.toString(),
//                "communityType" to communityType
//            )
//            it.findNavController().navigate(R.id.action_communityFragment_to_communityDetailFragment,bundle)
//
//        }
    }


    override fun getItemCount(): Int = comment.size

}