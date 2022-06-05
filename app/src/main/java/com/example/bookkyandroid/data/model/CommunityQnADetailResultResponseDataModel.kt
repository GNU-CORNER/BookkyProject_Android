package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityQnADetailResultResponseDataModel(
    @SerializedName("postdata")
    val postdata : CommunityDetailPostDataModel?,
    @SerializedName("replydata")
    val replydata : ArrayList<CommunityQnAReplyDetailDataModel>?,
    @SerializedName("commentCnt")
    val commentCnt : Int?,
    @SerializedName("replyCnt")
    val replyCnt : Int?,
    @SerializedName("Book")
    val Book : BookDetailDataModel?
)
