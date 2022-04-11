package com.example.bookkyandroid.data.model

data class HomeResultResponseDataModel(
    val bookList : ArrayList<HomeBookListDataModel>?,
    val communityList : ArrayList<HomeCommunityListDataModel>?,
    val userData : HomeUserDataModel?
    )