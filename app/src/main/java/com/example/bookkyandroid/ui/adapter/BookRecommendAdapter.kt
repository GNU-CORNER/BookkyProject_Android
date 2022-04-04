package com.example.bookkyandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.data.model.Message
import com.example.bookkyandroid.util.Constants.RECEIVE_ID
import com.example.bookkyandroid.util.Constants.RESULT_ID
import com.example.bookkyandroid.util.Constants.SEND_ID
import de.hdodenhof.circleimageview.CircleImageView


class BookRecommendAdapter (val messagesList : ArrayList<Message>): RecyclerView.Adapter<BookRecommendAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bookkyImg =  itemView.findViewById<CircleImageView>(R.id.book_recommend_recyclerview_item_imageView_bookky)
        val sendMessage = itemView.findViewById<TextView>(R.id.book_recommend_recyclerview_item_textView_send)
        val receiveMessage = itemView.findViewById<TextView>(R.id.book_recommend_recyclerview_item_textView_receive)
        val emptyView = itemView.findViewById<View>(R.id.book_recommend_recyclerview_item_view)

        init {
            // Define click listener for the ViewHolder's View.
        }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return BookRecommendAdapter.MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_book_recommend, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }


    override fun onBindViewHolder(holder: BookRecommendAdapter.MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        if (currentMessage.bookkyVisible == false) holder.bookkyImg.visibility =
            View.INVISIBLE else  holder.bookkyImg.visibility = View.VISIBLE

        when (currentMessage.id) {
            SEND_ID -> {
                holder.sendMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.receiveMessage.visibility = View.INVISIBLE
                holder.bookkyImg.visibility = View.INVISIBLE
            }
            RECEIVE_ID -> {
                holder.receiveMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.sendMessage.visibility = View.GONE
            }
            RESULT_ID -> {

                //아이템에 리사이클러뷰 나타내기


                val p = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                p.weight = 1f

                holder.receiveMessage.layoutParams = p
                holder.sendMessage.visibility = View.GONE
                holder.emptyView.visibility =  View.GONE
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }

}