package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityDetailResultResponseDataModel(
    @SerializedName("postdata")
    val postdata : CommunityDetailPostDataModel?,
    @SerializedName("commentdata")
    val commentdata : ArrayList<CommunityDetailCommentDataModel>?,
    @SerializedName("commentCnt")
    val commentCnt : Int?,
    @SerializedName("Book")
    val Book : BookDetailDataModel?
)
