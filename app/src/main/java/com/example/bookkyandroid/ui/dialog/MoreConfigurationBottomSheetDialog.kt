package com.example.bookkyandroid.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.ApplicationClass
import com.example.bookkyandroid.config.BookkyService
import com.example.bookkyandroid.config.TokenManager
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.WriteReviewResponseDataModel
import com.example.bookkyandroid.databinding.BottomsheetMoreConfigurationBinding
import com.example.bookkyandroid.ui.adapter.BottomSheetMoreConfigurationAdapter
import com.example.bookkyandroid.ui.adapter.getCall
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreConfigurationBottomSheetDialog(context : Context,private val itemList:ArrayList<Int>,private val RID:Int, private val findNavController: NavController, private val listener:getCall) : BottomSheetDialog(context) ,getActionItem{
    private lateinit var binding: BottomsheetMoreConfigurationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = BottomsheetMoreConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        bindingItem(itemList,RID)
        dismiss()
    }
    override fun show() {
        if(!this.isShowing) super.show()
    }

    override fun getAction(item: Int) {
        val DELETE_ITEM = 0
        val EDIT_ITEM = 1
        val REPORT_ITEM = 2
        CoroutineScope(Dispatchers.Main).launch {
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            val access_token = TokenManager.getInstance().access_token
            when(item){
                DELETE_ITEM->{
                    deleteItem(RID, bookkyService)
                    listener.getCallSuccess()
                    dismiss()
                }
                EDIT_ITEM->{
                    editItem(RID, bookkyService)
                    dismiss()
                }
                REPORT_ITEM->{
                    reportItem(RID, bookkyService)
                }
            }
        }

    }
    private fun bindingItem(itemList: ArrayList<Int>, RID:Int){

        binding.bottomsheetConfigurationRecyclerview.adapter = BottomSheetMoreConfigurationAdapter(itemList, RID,this)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.bottomsheetConfigurationRecyclerview.layoutManager = linearLayoutManager
    }
    private fun deleteItem(RID:Int, bookkyService: BookkyService){
        bookkyService.reviewDelete(RID)
            .enqueue(object : Callback<BaseResponse<String?>> {
                override fun onFailure(call: Call<BaseResponse<String?>>, t: Throwable) {
                }

                override fun onResponse(call: Call<BaseResponse<String?>>, response: Response<BaseResponse<String?>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                    }

                }
            })
    }
    private fun editItem(RID:Int, bookkyService: BookkyService){
        bookkyService.reviewGet(RID)
            .enqueue(object : Callback<BaseResponse<WriteReviewResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<WriteReviewResponseDataModel>>, t: Throwable) {
                }

                override fun onResponse(call: Call<BaseResponse<WriteReviewResponseDataModel>>, response: Response<BaseResponse<WriteReviewResponseDataModel>>){
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        val bundle = bundleOf("RID" to RID)
                        bundle.putString("thumbnail", it.result.review.thumbnail!!)
                        bundle.putString("TITLE",it.result.review.bookTitle!!)
                        bundle.putString("AUTHOR",it.result.review.AUTHOR!!)
                        bundle.putFloat("RATING",it.result.review.rating!!)
                        bundle.putString("contents", it.result.review.contents!!)
                        bundle.putInt("type", 0)
                        findNavController.navigate(R.id.action_bookDetailFragment_to_reviewWriteFragment, bundle)
                    }

                }
            })

    }
    private fun reportItem(RID:Int, bookkyService: BookkyService){

    }
}
interface getActionItem{
    fun getAction(item : Int)
}