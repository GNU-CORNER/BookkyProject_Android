package com.example.bookkyandroid.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.ReviewDataModel
import com.example.bookkyandroid.data.model.ReviewLikeResponseDataModel
import com.example.bookkyandroid.ui.dialog.MoreConfigurationBottomSheetDialog
import com.example.bookkyandroid.ui.fragment.book.callbackMoreAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailReviewAdapter(private val review: ArrayList<ReviewDataModel>, private val context : Context, private val listener : callbackMoreAPI) : RecyclerView.Adapter<RecyclerView.ViewHolder>() ,getCall{
    private val VIEW_NO_OBJECT_TYPE = 0
    private val VIEW_PAGE_TYPE = 1
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView = view.findViewById(R.id.textView_book_detail_review_item_title)
        val expand : TextView = view.findViewById(R.id.textView_book_detail_more)
        val contents : TextView = view.findViewById(R.id.textView_book_detail_review_item_contents)
        val like : TextView = view.findViewById(R.id.textView_book_detail_likeCnt)
        val date: TextView = view.findViewById(R.id.textView_book_detail_createAt)
        val ratingBar : RatingBar = view.findViewById(R.id.ratingBar_book_detail_review_item)
        var isExpanded : Boolean = false
        val ratingNum : TextView = view.findViewById(R.id.book_detail_review_item_textView_ratingNum)
        val likeButton : TextView = view.findViewById(R.id.textView_book_detail_like)
        val likeCnt : TextView = view.findViewById(R.id.textView_book_detail_likeCnt)
        val moreConfigurationButton : Button = view.findViewById(R.id.button_book_detail_bottm_action)
        init {
            expand.setOnClickListener {
                if(!isExpanded){
                    contents.maxLines = 9999
                    isExpanded = true
                }
                else{
                    contents.maxLines = 2
                    isExpanded = false
                }
            }


        }
    }
    class NoObjectViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val objectTitle : TextView = view.findViewById(R.id.recyclerview_textView_no_object_title)
    }

    override fun getItemViewType(position: Int): Int {
        return when(review[position].RID){
            0 -> VIEW_NO_OBJECT_TYPE
            else->VIEW_PAGE_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_PAGE_TYPE ->{
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_book_detail_review,
                    parent,
                    false
                )
                PagerViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_no_object,
                    parent,
                    false
                )
                NoObjectViewHolder(view)
            }
        }

    }

    override fun getCallSuccess() {
        listener.callAPI()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PagerViewHolder){
            holder.name.text = review[position].nickname
            holder.contents.text = review[position].contents
            holder.like.text = review[position].likeCnt.toString()
            holder.ratingBar.rating =  review[position].rating.toFloat()
            holder.date.text = review[position].createAt
            holder.ratingNum.text = "("+review[position].rating.toString()+")"
            holder.likeCnt.text = review[position].likeCnt.toString()
            holder.moreConfigurationButton.setOnClickListener {
                val data = arrayListOf(1,0,2)
                MoreConfigurationBottomSheetDialog(context,data,review[position].RID, it.findNavController(), this).show()

            }
            holder.likeButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val bookkyService = ApplicationClass.getInstance().getRetrofit()
                    bookkyService.likeReview(review[position].RID)
                        .enqueue(object : Callback<BaseResponse<ReviewLikeResponseDataModel>> {
                            override fun onFailure(call: Call<BaseResponse<ReviewLikeResponseDataModel>>, t: Throwable) {
                            }

                            override fun onResponse(call: Call<BaseResponse<ReviewLikeResponseDataModel>>, response: Response<BaseResponse<ReviewLikeResponseDataModel>>){
                                if (response.isSuccessful.not()) {
                                    return
                                }
                                response.body()?.let {
                                    if(it.result.isLiked){
                                        runBlocking {
                                            holder.likeCnt.text =
                                                (Integer.parseInt(holder.likeCnt.text.toString()) + 1).toString()
                                            holder.likeButton.setTextColor(Color.parseColor("#6C95FF"))
                                            holder.likeCnt.setTextColor(Color.parseColor("#6C95FF"))
                                        }
                                    }else{
                                        runBlocking {
                                            holder.likeCnt.text =
                                                (Integer.parseInt(holder.likeCnt.text.toString()) - 1).toString()
                                            holder.likeButton.setTextColor(Color.parseColor("#8A8585"))
                                            holder.likeCnt.setTextColor(Color.parseColor("#8A8585"))
                                        }
                                    }
                                }

                            }
                        })
                }
            }
        }
        else if (holder is NoObjectViewHolder){
            holder.objectTitle.text = "작성된 후기가 없습니다."
        }

    }

    override fun getItemCount(): Int = review.size

}
interface getCall{
    fun getCallSuccess()
}