package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class SearchResultResponseDataModel(
    @SerializedName("BID")
    val BID : Int,
    @SerializedName("AUTHOR")
    val AUTHOR : String,
    @SerializedName("TITLE")
    val TITLE : String,
    @SerializedName("thumbnailImage")
    val thumbnailImage : String,
    @SerializedName("RATING")
    val RATING : Float,
    @SerializedName("tagData")
    val tagData : ArrayList<TagDataResponseDataModel>
)
/*
 "BID": 3,
      "TITLE": "공학텍스트북 파이썬 수치해석",
      "AUTHOR": "Qingkai Kong 외 지음, 이광수 옮김",
      "thumbnailImage": "http://203.255.3.144:8010/thumbnail/bookThumbnail/288830929.png",
      "RATING": 4.1000000000000005,
      "BOOK_INTRODUCTION": null,
      "PUBLISH_DATE": "2022-02-18",
      "PUBLISHER": "휴먼싸이언스",
      "tagData": [
        {
          "tag": "파이썬",
          "TID": 1
        },
        {
          "tag": "수학",
          "TID": 134
        },
        {
          "tag": "초보자",
          "TID": 119
        },
        {
          "tag": "라인",
          "TID": 132
        },
        {
          "tag": "디버깅",
          "TID": 146
        },
        {
          "tag": "리버싱",
          "TID": 147
        },
        {
          "tag": "객체지향 프로그래밍",
          "TID": 2
        },
        {
          "tag": "알고리즘",
          "TID": 20
        }
      ]

 */