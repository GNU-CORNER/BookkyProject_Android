package com.example.bookkyandroid.config

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenIntercepter(
    private val bookkyService: BookkyService,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 1. MobileToken을 꺼내 Request Header에 넣고 요청을 보낸다.
        val mobileToken = TokenManager.getInstance()
        val tokenAddedRequest = chain.request().putTokenHeader(mobileToken)

        val response = chain.proceed(tokenAddedRequest)

        // 2. 위 Response에서 응답 json을 꺼내 서버 응답 코드가 토큰 만료 에러 코드인지 확인한다.
        if (response.isSuccessful.not()) { // 응답 토큰이 만료되었는지에 대한 메서드는 비공개합니다!

            // 5. chain의 Request 객체를 복사해 재발급한 토큰을 Header에 넣고 요청을 보낸다.
            val refreshedRequest = chain.request().putTokenHeader(mobileToken)
            return chain.proceed(refreshedRequest)
        }

        // 3. 토큰 만료에러가 아니면 응답을 그대로 반환 한다.
        return response
    }

    private fun Request.putTokenHeader(tokenManager: TokenManager): Request {
        return this.newBuilder()
//            .addHeader(AUTHORIZATION, tokenManager.accessToken)
//            .addHeader(AUTHORIZATION, tokenManager.refreshToken)
            .build()
    }

    companion object {
        private const val AUTHORIZATION = "authorization"
    }

}