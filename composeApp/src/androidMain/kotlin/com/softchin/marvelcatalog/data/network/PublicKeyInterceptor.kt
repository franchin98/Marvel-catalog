package com.softchin.marvelcatalog.data.network

import com.softchin.marvelcatalog.core.PUBLIC_KEY
import okhttp3.Interceptor
import okhttp3.Response

class PublicKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        val newUrl = url.newBuilder()
            .addQueryParameter("apikey", PUBLIC_KEY)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}