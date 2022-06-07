package com.example.bookkyandroid.config

import android.util.Log
import com.example.bookkyandroid.data.model.*
import com.example.bookkyandroid.data.model.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

interface BookkyService {

    //홈 GET 호출
    @GET("/v1/home")
    fun getHomeData(
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
    ): Call<BaseResponse<HomeMoreTagResponseDataModel>>

    @GET("/v1/books/tag/{TID}")
    fun getHomeMoreTagDetailData(
        @Path("TID") TID:Int,
        @Query("quantity") qunatity :Int,
        @Query("page") page:Int
    ): Call<BaseResponse<HomeMoreTagDetailResponseDataModel>>

    @GET("/v1/tags")
    fun getTagItem(): Call<BaseResponse<SurveySelectorResponseDataModel>>

    @POST("/v1/auth/refresh")
    fun refreshToken(
    ) : Call<BaseResponse<AuthRefreshResponseDataModel>>

    @GET("/v1/myprofile")
    fun getMyprofile(
    ) : Call<BaseResponse<MyProfileResponseDataModel>>

    @GET("/v1/books/detail/{pk}")
    fun getBookDetail(
        @Path("pk") pk:Int,
    ) : Call<BaseResponse<BookDetailResponseDataModel>>

    @GET("/v1/user/favoritebook/0")
    fun getFavoriteBook(
    ) : Call<BaseResponse<FavoriteBookListResponseDataModel>>

    @GET("/v1/user/myreview")
    fun getMyReview(
    ) : Call<BaseResponse<MyReviewResponseDataModel>>

    @GET("/v1/books/search")
    fun searchBook(
        @Query("keyword") keyword : String,
        @Query("quantity") quantity : Int,
        @Query("page") page : Int
    ) : Call<BaseResponse<SearchResultResponseDataModel>>

    @POST("/v1/user/check")
    fun checkAuthenticationCode(
        @Body userAuthenticationCode :UserAuthenticationCode
    ) : Call<BaseResponse<String?>>

    @PUT("/v1/auth/password/init")
    fun initPassword(
        @Body initPasswordBodyDataModel: InitPasswordBodyDataModel
    ) : Call<BaseResponse<String?>>

    @GET("/v1/auth/email")
    fun emailAuth(
        @Query("email") email: String
    ): Call<UserEmailResponseDataModel>

    @PUT("/v1/user/tag")
    fun updateTag(
        @Body tag : UpdateTagBodyDataModel
    ): Call<BaseResponse<UpdateTagResponseDataModel>>

    @GET("/v1/community/postdetail/{slug1}/{slug2}")
    fun getCommunityDetailData(
         @Path("slug1")slug1: String?,
         @Path("slug2")slug2: String?,
         @Query("mode") mode: Int
    ): Call<CommunityDetailResponseDataModel>

    @GET("/v1/community/postdetail/{slug1}/{slug2}")
    fun getQnACommunityDetailData(
        @Path("slug1")slug1: String?,
        @Path("slug2")slug2: String?,
        @Query("mode") mode: Int
    ): Call<CommunityQnADetailResponseDataModel>

    @GET("/v1/community/hotcommunity")
    fun getHotCommunitiyData(
        @Query("quantity") quantity : Int,
        @Query("page") page : Int
    ): Call<CommunityResponseDataModel>

    @GET("/v1/community/postlist/{slug}")
    fun getCommunitiyData(
        @Path("slug")slug: String?,
        @Query("quantity") quantity : Int,
        @Query("page") page : Int
    ): Call<CommunityResponseDataModel>

    @GET("/v1/user/mypost")
    fun getMyPost(
    ): Call<BaseResponse<MyProfilePostResponseDataModel>>

    @PUT("/v1/user/myprofile")
    fun updateMyProfile(
        @Body profileData : UpdateProfileBodyDataModel
    ): Call<BaseResponse<ModifyProfileResponseDataModel>>

    @POST("/v1/user/favoritebook/{slug}")
    fun favoriteBook(
        @Path("slug") pk:Int,
    ) : Call<BaseResponse<PostFavoriteBookDataModel>>

    @POST("/v1/review/{id}")
    fun writeReview(
        @Path("id") id:Int,
        @Body body : WriteReviewBodyDataModel
    ) : Call<BaseResponse<WriteReviewResponseDataModel>>

    @PUT("/v1/review/like/{id}")
    fun likeReview(
        @Path("id") id:Int,
    ) : Call<BaseResponse<ReviewLikeResponseDataModel>>

    @POST("/v1/community/writepost/{slug}")
    fun writePost(
        @Path("slug") id:Int,
        @Body postBody :WritePostBodyDataModel
    ) : Call<BaseResponse<String>>

    @POST("/v1/community/writecomment/{slug}")
    fun writeComment(
        @Path("slug") id:Int,
        @Body postBody :WriteCommentBodyDataModel
    ) : Call<BaseResponse<String>>


    @GET("/v1/community/post/book")
    fun searchWriteBook(
        @Query("keyword") keyword : String,
        @Query("quantity") quantity : Int,
        @Query("page") page : Int
    ) : Call<BaseResponse<WriteBookSearchResponseDataModel>>

    @PUT("/v1/review/{id}")
    fun reviewUpdate(
        @Path("id") RID : Int,
        @Body body : WriteReviewBodyDataModel
    ) : Call<BaseResponse<WriteReviewResponseDataModel>>

    @DELETE("/v1/review/{id}")
    fun reviewDelete(
        @Path("id") RID : Int,
    ) : Call <BaseResponse<String?>>

    @GET("/v1/review/{id}")
    fun reviewGet(
        @Path("id") RID : Int,
    ) : Call<BaseResponse<WriteReviewResponseDataModel>>

    companion object {
        private const val BASE_URL =
            "http://203.255.3.144:8002"
        fun create(): BookkyService {
            val logger = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BASIC
            }
            val interceptor = Interceptor { chain ->
                with(chain) {
                    refresh_token(RetrofitManager.bookkyService)

                    val newRequest = request().newBuilder()
                        .addHeader("access-token", TokenManager.getInstance().access_token)
                        .addHeader("refresh-token", TokenManager.getInstance().refresh_token)
                        .build()
                    proceed(newRequest)
                }
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookkyService::class.java)
        }
        fun refresh_token(bookkyService:BookkyService){
            bookkyService.refreshToken()
                .enqueue(object : Callback<BaseResponse<AuthRefreshResponseDataModel>> {
                    override fun onFailure(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, t: Throwable) {
                    }
                    override fun onResponse(call: Call<BaseResponse<AuthRefreshResponseDataModel>>, response: Response<BaseResponse<AuthRefreshResponseDataModel>>) {
                        if (response.isSuccessful.not()) {
                            Log.d("refresh", response.toString())
                            return
                        }
                        response.body()?.let {
                            CoroutineScope(Dispatchers.Main).launch {
                                ApplicationClass.getInstance().getDataStore()
                                    .setAccessToken(it.result.access_token)
                                TokenManager.getInstance().getToken()
                                ApplicationClass.getInstance().recreateRetrofit()

                            }

                        }
                    }
                })
        }
    }

}