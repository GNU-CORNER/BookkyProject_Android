package com.example.bookkyandroid.ui.fragment.search

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import com.example.bookkyandroid.R
import com.example.bookkyandroid.config.*
import com.example.bookkyandroid.databinding.FragmentSearchBinding
import com.example.bookkyandroid.ui.adapter.RecentKeywordListAdapter


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbController = DBController.getInstance()
        val dbHelper = dbController.getter()
        loadDB(dbHelper!!)
        binding.imageButton4.setOnClickListener{
            val writeDB = dbHelper.writableDatabase
            val keyword = binding.searchEditTextSearchInput.text.toString()
            val values = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, keyword)
            }
            val a = writeDB?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
            Log.d("db", a.toString())
            val bundle = bundleOf("keyword" to keyword)
            findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment2, bundle)
        }

    }
    private fun loadDB(dbHelper: FeedReaderContract.FeedReaderDbHelper){
        val readDB = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)
        val sortOrder = "${BaseColumns._ID} DESC"

        val cursor = readDB.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        val itemTextArray = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val itemText = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE))
                itemTextArray.add(itemText)
            }
        }
        cursor.close()
        if (itemTextArray.size==0){
            val keywords = arrayListOf("최근 검색기록이 없어요...")
            recentKeywordAdapter(keywords)

            binding.recyclerViewRecentKeywordList.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return true
                }
            })
        }
        else{
            recentKeywordAdapter(itemTextArray as ArrayList<String>)
        }
    }
    private fun recentKeywordAdapter(keyword : ArrayList<String>){
        binding.recyclerViewRecentKeywordList.adapter = RecentKeywordListAdapter(keyword, binding)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewRecentKeywordList.layoutManager = linearLayoutManager
    }
}
