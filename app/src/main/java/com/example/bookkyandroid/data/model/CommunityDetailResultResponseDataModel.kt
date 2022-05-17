package com.example.bookkyandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommunityDetailResultResponseDataModel(
    @SerializedName("postdata")
    val postdata : ArrayList<CommunityDetailPostDataModel>?,
    @SerializedName("postuserdata")
    val postuserdata : ArrayList<CommunityDetailPostUserDataModel>?,
    @SerializedName("commentdata")
    val commentdata : ArrayList<CommunityDetailCommentDataModel>?,
    @SerializedName("commentuserdata")
    val commentuserdata : ArrayList<CommunityDetailCommentUserDataModel>?
)
