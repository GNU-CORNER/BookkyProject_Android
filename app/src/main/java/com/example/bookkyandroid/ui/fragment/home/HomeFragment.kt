package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.Book
import com.example.bookkyandroid.data.model.HomeCommunityDataModel
import com.example.bookkyandroid.databinding.FragmentHomeBinding
import com.example.bookkyandroid.ui.adapter.HomeCommunityShortAdapter
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.bookkyandroid.data.model.BookResult
import retrofit2.http.GET
import retrofit2.http.Query


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bookService = retrofit.create(HomeFragment.BookGetCaller::class.java)
        bookService.getBookList("25", "1")
            .enqueue(object : Callback<Book> {
                override fun onFailure(call: Call<Book>, t: Throwable) {
                    Log.d(HomeFragment.TAG, t.toString())
                }

                override fun onResponse(call: Call<Book>, response: Response<Book>) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        Log.d(HomeFragment.TAG, it.toString())

                        it.result.forEach { bookResult ->
                            Log.d(TAG, bookResult.toString())
                        }
                        myTagBooksAdapterSet1(it.result)
                        myTagBooksAdapterSet2(it.result)
                    }
                }
            })

        var textNickname: TextView = binding.textViewHomeNickName
        textNickname.text = "황랑귀"
        val bookList = arrayListOf<String>("test1", "test2", "test3","test3","test3","test3","test3")
        val communityDummyData = arrayListOf<HomeCommunityDataModel>(
            HomeCommunityDataModel("핫게시판", "한 번 읽어보고 마스터한 책 사실 분~!!!"),
            HomeCommunityDataModel("QnA게시판", "함수를 썼는데 이상해요..."),
            HomeCommunityDataModel("자유게시판", "카카오 공채 떴던데 보신 분 있으신가요?")
        )
        homeCommunitySet(communityDummyData)
    }

    private fun myTagBooksAdapterSet1(titles: List<BookResult>) {
        binding.recyclerViewHomeBookList1.adapter = HomeTagByBooksAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList1.layoutManager = linearLayoutManager
    }
    private fun myTagBooksAdapterSet2(titles: List<BookResult>) {
        binding.recyclerViewHomeBookList2.adapter = HomeTagByBooksAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList2.layoutManager = linearLayoutManager
    }
    private fun homeCommunitySet(titles: ArrayList<HomeCommunityDataModel>) {
        binding.recyclerViewHomeCommunityList.adapter = HomeCommunityShortAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHomeCommunityList.layoutManager = linearLayoutManager
        binding.recyclerViewHomeCommunityList.stopScroll()
    }
    companion object{
        private const val TAG="HomeFragment"
    }
    public interface BookGetCaller {
        @GET("/v1/test2/0")
        fun getBookList(
            @Query("quantity") quantity: String,
            @Query("page") page: String
        ): Call<Book>
    }

}
