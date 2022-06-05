package com.example.bookkyandroid.ui.adapter

import android.os.Bundle
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
import com.example.bookkyandroid.data.model.WriteBookSearchDataModel
import com.example.bookkyandroid.ui.dialog.getDatafromDialog

class BottomSheetSearchResultListAdapter (private val searchData : ArrayList<WriteBookSearchDataModel>, listener : getDatafromDialog) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_LODING_TYPE = 0
    private val VIEW_PAGE_TYPE = 1
    val dialoglistener = listener
    fun deleteLoading(){
        searchData.removeAt(searchData.lastIndex) // 로딩이 완료되면 프로그레스바를 지움
    }
    class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle : TextView = view.findViewById(R.id.recyclerview_item_bottomsheet_textView_bookTitle)
        val bookAuthor : TextView = view.findViewById(R.id.recyclerview_item_bottomsheet_textView_bookAuthor)
        val bookImage : ImageView = view.findViewById(R.id.recyclerview_item_bottomsheet_imageView_book)
        init {
            // Define click listener for the ViewHolder's View.
        }
    }
    class LodingViewHolder(view : View) : RecyclerView.ViewHolder(view){

    }
    override fun getItemViewType(position: Int): Int {
        // 게시물과 프로그레스바 아이템뷰를 구분할 기준이 필요하다.
        return when (searchData[position].TITLE) {
            " " -> VIEW_LODING_TYPE
            else -> VIEW_PAGE_TYPE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            VIEW_PAGE_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_bottomsheet_book,
                    parent,
                    false
                )
                PagerViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_loading_bar,
                    parent,
                    false
                )
                LodingViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PagerViewHolder){
            holder.bookAuthor.text = searchData[position].AUTHOR + " / " +searchData[position].PUBLISHER
            holder.bookTitle.text = searchData[position].TITLE
            holder.apply {
                Glide.with(itemView)
                    .load(searchData[position]!!.thumbnailImage) // 불러올 이미지 url
                    .placeholder(R.drawable.test_book) // 이미지 로딩 시작하기 전 표시할 이미지
                    .error(R.drawable.test_book) // 로딩 에러 발생 시 표시할 이미지
                    .fallback(R.drawable.test_book) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                    .into(holder.bookImage) // 이미지를 넣을 뷰
                //이미지 뷰 처리는 Glide 라이브러리 사용 예정
            }
            holder.itemView.setOnClickListener{
                val data =WriteBookSearchDataModel(searchData[position].TBID,searchData[position].TITLE,searchData[position].AUTHOR,searchData[position].thumbnailImage,searchData[position].PUBLISHER, searchData[position].tagData)
                dialoglistener.loadData(data)

            }
        }else{

        }
    }

    override fun getItemCount(): Int = searchData.size


}