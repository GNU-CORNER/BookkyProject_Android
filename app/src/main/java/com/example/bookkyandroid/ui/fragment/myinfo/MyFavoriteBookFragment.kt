package com.example.bookkyandroid.ui.fragment.myinfo

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.BaseResponse
import com.example.bookkyandroid.data.model.FavoriteBookDataModel
import com.example.bookkyandroid.data.model.FavoriteBookListResponseDataModel
import com.example.bookkyandroid.databinding.FragmentMyFavoriteBookBinding
import com.example.bookkyandroid.ui.adapter.MyFavoriteBookAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFavoriteBookFragment: BaseFragment<FragmentMyFavoriteBookBinding>(
    FragmentMyFavoriteBookBinding::bind, R.layout.fragment_my_favorite_book) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val bookkySerivce = ApplicationClass.getInstance().getRetrofit()
            getFavoriteData(bookkySerivce)
        }

    }
    private fun favoriteBookListAdapter(DataModels: ArrayList<FavoriteBookDataModel>){
        binding.recyclerViewMyFavoriteBook.adapter = MyFavoriteBookAdapter(DataModels)
        val linearLayoutManager = GridLayoutManager(activity,3)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMyFavoriteBook.layoutManager = linearLayoutManager
    }
    private fun getFavoriteData(bookkyService: BookkyService){
        bookkyService.getFavoriteBook()
            .enqueue(object : Callback<BaseResponse<FavoriteBookListResponseDataModel>> {
                override fun onFailure(call: Call<BaseResponse<FavoriteBookListResponseDataModel>>, t: Throwable) {

                }

                override fun onResponse(call: Call<BaseResponse<FavoriteBookListResponseDataModel>>, response: Response<BaseResponse<FavoriteBookListResponseDataModel>>){
                    if (response.isSuccessful.not()) {

                        return
                    }
                    response.body()?.let {
                        favoriteBookListAdapter(it.result.favoriteBookList)
                    }
                }
            })
    }
}