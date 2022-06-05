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
import com.example.bookkyandroid.data.model.CommunityQnADetailResponseDataModel
import com.example.bookkyandroid.data.model.CommunityQnAReplyDetailDataModel
import com.example.bookkyandroid.data.model.MyWriting
import com.example.bookkyandroid.ui.fragment.community.CommunityDetailFragment
import com.example.bookkyandroid.ui.fragment.community.CommunityFragmentDirections

class CommunityQnADetailReplyAdapter(private val reply: ArrayList<CommunityQnAReplyDetailDataModel>, val communityType: String) : RecyclerView.Adapter<CommunityQnADetailReplyAdapter.PagerViewHolder>() {

    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nickname : TextView = view.findViewById(R.id.community_textView_nickname)
        val contents : TextView = view.findViewById(R.id.community_textView_contents)
        val updateAt : TextView = view.findViewById(R.id.community_textView_updatedAt)
        val likeCnt: TextView = view.findViewById(R.id.community_textView_likeCnt)
        val commentCnt: TextView = view.findViewById(R.id.community_textView_commentCnt)

//        val layout : LinearLayout = view.findViewById(R.id.community_linearLayout_recyclerview_item)
//       가로 메뉴 버튼!
        init {
            // Define click listener for the ViewHolder's View.

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_community_reply_post_detail,
            parent,
            false
        )
        return PagerViewHolder(view)
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {

        holder.nickname.text = reply[position].nickname
        holder.contents.text = reply[position].contents
        holder.likeCnt.text = "좋아요("+ reply[position].like?.size.toString()+")"
        holder.updateAt.text = reply[position].updateAt
        holder.commentCnt.text = "댓글("+reply[position].commentCnt.toString()+")"
//        holder.layout.setOnClickListener{
//            val bundle = bundleOf(
//                "PID" to myWriting[position].PID.toString(),
//                "communityType" to communityType
//            )
//            it.findNavController().navigate(R.id.action_communityFragment_to_communityDetailFragment,bundle)
//
//        }
    }


    override fun getItemCount(): Int = reply.size

}