package com.example.bookkyandroid.config

import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface BookkyService {

    //홈 GET 호출
    @GET("/v1/home")
    fun getHomeData(
        @Header("access-token") access_token: String?
    ): Call<HomeResponseDataModel>

    //로그인 POST 호출
    @POST("/v1/user/signin")
    fun signIn(
        @Body userSignInbody: UserSignInBody
    ): Call<UserSignInResponse>

    //회원가입 POST 호출
    @POST("/v1/user/signup")
    fun signUp(
        @Body userSignUpBody: UserSignUpBody
    ): Call<UserSignUpResponse>

    //인증번호 전송 GET 호출
    @GET("/v1/user/email")
    fun sendTo(
        @Query("email") email: String
    ): Call<UserEmailResponseDataModel>

    @GET("/v1/home/tags")
    fun getHomeMoreTagData(
        @Header("access-token") access_token: String?
    ): Call<BaseResponse<HomeMoreTagResponseDataModel>>

    @GET("/v1/books/tag/{TID}")
    fun getHomeMoreTagDetailData(
        @Path("TID")TID:Int?,
        @Query("quantity")qunatity :Int?,
        @Query("page")page:Int?
    ): Call<BaseResponse<HomeBookListDataModel>>
}