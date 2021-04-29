package com.brianperin.githubrepository.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.brianperin.githubrepository.BuildConfig
import com.brianperin.githubrepository.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ApolloClientImpl {

    val apiClient: ApolloClient

    init {

        val cacheFactory = LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build())

        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor { chain ->

                val original = chain.request()
                val builder = original.newBuilder()

                builder.header(Constants.ACCEPT, Constants.APPLICATION_JSON)
                builder.header(Constants.ACCEPT_ENCODING, Constants.APPLICATION_JSON)
                builder.header("Authorization", "Bearer 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")

                builder.method(original.method, original.body)

                val originalRequest = chain.proceed(builder.build())
                val newRequest = originalRequest.newBuilder()

                newRequest.build()
            }
            .build()

        apiClient = ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .normalizedCache(cacheFactory)
            .okHttpClient(okHttpClient)
            .build()
    }


}