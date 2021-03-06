package com.example.bookkyandroid.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.data.model.HomeBookListDataModel
import com.example.bookkyandroid.data.model.HomeCommunityDataModel
import com.example.bookkyandroid.data.model.HomeResponseDataModel
import com.example.bookkyandroid.databinding.FragmentHomeBinding
import com.example.bookkyandroid.ui.adapter.HomeCommunityShortAdapter
import com.example.bookkyandroid.ui.adapter.HomeTagByBooksAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {
    private var data : HomeResponseDataModel? = null
    private var type : String? = null
    private var flag = false
    private var flag2 = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            //API 호출 BACK THREAD에서 호출 Coroutine
            val bookkyService = ApplicationClass.getInstance().getRetrofit()
            getHomeData(bookkyService)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getString("type")

        if (data != null){
            successToGetHome(data!!.result.userData!!.nickname)
            homeCommunitySet(data!!.result.communityList!!)
            homeBookListAdapterSet1(
                data!!.result.bookList!![0].tag,
                data!!.result.bookList!![0]
            )
            homeBookListAdapterSet2(
                data!!.result.bookList!![1].tag,
                data!!.result.bookList!![1]
            )
        }
        if (type != null && flag == false){
                ApplicationClass.getInstance().showLoadingDialog(requireContext())
                flag = true
                flag2 = false
                CoroutineScope(Dispatchers.IO).launch {
                    val bookkyService = ApplicationClass.getInstance().getRetrofit()
                    getHomeData(bookkyService)
                }
        }
        binding.textViewHomeCommunityHeadLineText.setOnClickListener {
            var flag = false
            CoroutineScope(Dispatchers.IO).launch {
                if (TokenManager.getInstance().access_token.length == 0){
                    flag = true
                }
            }
            if (!flag){
//                findNavController().navigate(R.id.c)
            }
            else{
                findNavController().navigate(R.id.action_global_signInFragment)
            }
        }
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_surveyFragment)
        }
        binding.textViewHomeTagMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeMoreTagFragment)
        }
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_global_signInFragment)
        }
        binding.frameLayoutBookRecommendRoadmapButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_suggestionFragment)
        }
        binding.frameLayoutRecommendBookynatorButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bookRecommendFragment)
        }

    }
    // Fragment 새로고침

// Fragment 클래스에서 사용 시


    private fun successToGetHome(nickname : String?){
        if (nickname == null){
            binding.textViewHomeNickName.text = "처음 온 당신"
            binding.textViewHomeHeadLine2.text ="에게"
        }
        else{
            binding.textViewHomeNickName.setText(nickname)
        }

    }
    private fun homeBookListAdapterSet1(headline : String, DataModels: HomeBookListDataModel){
        binding.homeTextViewRecyclerviewHeadline1.text = headline
        binding.recyclerViewHomeBookList1.adapter = HomeTagByBooksAdapter(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList1.layoutManager = linearLayoutManager
        binding.homeTextViewRecyclerviewHeadline1.setOnClickListener {
            val bundle = bundleOf("TID" to DataModels.TMID)
            it.findNavController().navigate(R.id.homeMoreTagDetailFragment, bundle)
        }
    }

    private fun homeBookListAdapterSet2(headline : String,DataModels: HomeBookListDataModel){
        binding.homeTextViewRecyclerviewHeadline2.text = headline
        binding.recyclerViewHomeBookList2.adapter = HomeTagByBooksAdapter(DataModels)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewHomeBookList2.layoutManager = linearLayoutManager
        binding.homeTextViewRecyclerviewHeadline2.setOnClickListener {
            val bundle = bundleOf("TID" to DataModels.TMID)
            it.findNavController().navigate(R.id.homeMoreTagDetailFragment, bundle)
        }
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
    private fun reCall(){
            CoroutineScope(Dispatchers.IO).launch {
                //API 호출 BACK THREAD에서 호출 Coroutine
                Log.d("recall", "recalllitttttt")
                RetrofitManager.refresh_token()
                val bookkyService = ApplicationClass.getInstance().getRetrofit()
                getHomeData(bookkyService)
            }
    }
    private fun getHomeData(bookkyService: BookkyService){
        bookkyService.getHomeData()
            .enqueue(object : Callback<HomeResponseDataModel> {
                override fun onFailure(call: Call<HomeResponseDataModel>, t: Throwable) {
                }

                override fun onResponse(call: Call<HomeResponseDataModel>, response: Response<HomeResponseDataModel>){
                    if (response.code() == 401) {
                        reCall()

                    }
                    response.body()?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            successToGetHome(it.result.userData!!.nickname)
                            homeCommunitySet(it.result.communityList!!)
                            homeBookListAdapterSet1(
                                it.result.bookList!![0].tag,
                                it.result.bookList!![0]
                            )
                            homeBookListAdapterSet2(
                                it.result.bookList!![1].tag,
                                it.result.bookList!![1]
                            )
                        }
                        data = it
                    }

                }
            })
        sleep(2000)
        if(flag2){
            ApplicationClass.getInstance().dismissSplashDialog()
        }

        if(flag == true) {
            flag2 = true
            ApplicationClass.getInstance().dismissLoadingDialog()
            type = null
        }
    }
}

