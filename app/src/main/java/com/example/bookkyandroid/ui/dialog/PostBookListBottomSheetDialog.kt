package com.example.bookkyandroid.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.BottomsheetPostBookListBinding
import com.example.bookkyandroid.ui.adapter.BottomSheetSearchNoResultListAdapter
import com.example.bookkyandroid.ui.adapter.BottomSheetSearchResultListAdapter
import com.example.bookkyandroid.ui.fragment.community.getDatafromBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostBookListBottomSheetDialog(context: Context, listner: getDatafromBottomSheet) : BottomSheetDialog(context),getDatafromDialog {
    private var page = 1
    private var totalPage = 1
    private var flag = false
    var communityCallBackListner = listner
    private lateinit var binding: BottomsheetPostBookListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomsheetPostBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val bookkyService = ApplicationClass.getInstance().getRetrofit()
        binding.bottomsheetPostListImageButtonDissmissbutton.setOnClickListener {
            dismiss()
        }
        binding.bottomsheetImageButtonSearchButton.setOnClickListener {
            searchBook(binding.bottomsheetEditTextInputSearch.text.toString(),bookkyService)
        }
        binding.bottomsheetRecyclerviewBookList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                // 스크롤이 끝에 도달했는지 확인
                if (!binding.bottomsheetRecyclerviewBookList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
//                    SearchResultListAdapter.deleteLoading()
                    if (!flag) {
                        searchBook(binding.bottomsheetEditTextInputSearch.text.toString(), bookkyService)
                    }
                }
            }
        })
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }

    override fun loadData(data: WriteBookSearchDataModel) {
        communityCallBackListner.getData(data)
        dismiss()
    }
    private fun recyclerviewBinder(searchData: ArrayList<WriteBookSearchDataModel>) {
        if (searchData.size > 0) {
            searchData.add(
                WriteBookSearchDataModel(
                    0,
                    " ",
                    " ",
                    " ",
                    " ",
                    arrayListOf()
                )
            )
            binding.bottomsheetRecyclerviewBookList.adapter = BottomSheetSearchResultListAdapter(searchData, this)
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.bottomsheetRecyclerviewBookList.layoutManager = linearLayoutManager
            binding.bottomsheetRecyclerviewBookList.adapter!!.notifyItemRangeInserted(
                (page - 1) * 25,
                25
            )
        } else {
            binding.bottomsheetRecyclerviewBookList.adapter =
                BottomSheetSearchNoResultListAdapter("searchData")
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.bottomsheetRecyclerviewBookList.layoutManager = linearLayoutManager
        }
    }

    private fun searchBook(keyword: String,bookkyService: BookkyService) {
        bookkyService.searchWriteBook(keyword, 25, page)
            .enqueue(object : Callback<BaseResponse<WriteBookSearchResponseDataModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<WriteBookSearchResponseDataModel>>,
                    t: Throwable
                ) {
                    flag = true
                    recyclerviewBinder(arrayListOf())

                }
                override fun onResponse(
                    call: Call<BaseResponse<WriteBookSearchResponseDataModel>>,
                    response: Response<BaseResponse<WriteBookSearchResponseDataModel>>
                ) {
                    if (response.code() == 204) {
                        return
                    }
                    response.body()?.let {
                        recyclerviewBinder(it.result.searchData)
                        totalPage = it.result.total
                        page++
                    }
                }
            })
    }
}
interface getDatafromDialog{
    fun loadData(data : WriteBookSearchDataModel)
}