package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.BaseFragment
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.databinding.FragmentHomeBinding
import com.example.bookkyandroid.ui.adapter.HomeCommunityShortAdapter
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        val retrofit = Retrofit.Builder()
            .baseUrl("http://203.255.3.144:8002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bookService = retrofit.create(HomeFragment.BookGetCaller::class.java)
        bookService.getBookList("25", "1")
            .enqueue(object : Callback<BookResponse> {
                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    Log.d(HomeFragment.TAG, t.toString())
                }

                override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        Log.d(HomeFragment.TAG, it.toString())

                        it.result.forEach { bookResult ->
                            Log.d(TAG, bookResult.toString())
                        }
                        //myTagBooksAdapterSet(it.result)
                    }
                }
            })

        */

        var textNickname: TextView = binding.textViewHomeNickName
        textNickname.text = "황랑귀"
        val bookList = arrayListOf<String>("test1", "test2", "test3","test3","test3","test3","test3")
        val communityDummyData = arrayListOf<HomeCommunityDataModel>(
            HomeCommunityDataModel("핫게시판", "한 번 읽어보고 마스터한 책 사실 분~!!!"),
            HomeCommunityDataModel("QnA게시판", "함수를 썼는데 이상해요..."),
            HomeCommunityDataModel("자유게시판", "카카오 공채 떴던데 보신 분 있으신가요?")
        )
        homeCommunitySet(communityDummyData)



        //test set
        val testData : ArrayList<HomeTagTestResponse> = arrayListOf(
            HomeTagTestResponse(
                "DeepLearning >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "JavaScript >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "Kotlin >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "Java >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "Web >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "BackEnd >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "MVVM >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "MVI >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "MVP >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "MVC >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "Android >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            ),

            HomeTagTestResponse(
                "Clean Architecture >",
                arrayListOf(
                    HomeBookTestResponse("Deep Learing"),
                    HomeBookTestResponse("혼자 공부하는 머신러닝 + 딥러닝"),
                    HomeBookTestResponse("개를 다루는 기술"),
                    HomeBookTestResponse("REACT 리액트")
                )
            )

        )



        var isExpanded : Boolean = false

        myTagBooksAdapterSet(testData, isExpanded)

        binding.textViewHomeTagMore.setOnClickListener {
            isExpanded = if (!isExpanded) {
                myTagBooksAdapterSet(testData, isExpanded)
                true
            } else {
                myTagBooksAdapterSet(testData, isExpanded)
                false
            }
        }


    }

    private fun myTagBooksAdapterSet(testData: ArrayList<HomeTagTestResponse>, isExpanded : Boolean) {
        binding.recyclerViewHomeBookList.adapter = HomeTagByBooksAdapter(testData, isExpanded)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHomeBookList.layoutManager = linearLayoutManager
    }

    /*
    private fun myTagBooksAdapterSet(titles: List<BookResult>) {
        binding.recyclerViewHomeBookList.adapter = HomeTagByBooksAdapter(titles)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList.layoutManager = linearLayoutManager
    }*/
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
        ): Call<BookResponse>
    }

}
