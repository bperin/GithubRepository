package com.brianperin.githubrepository.network

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.brianperin.githubrepository.BuildConfig
import com.brianperin.githubrepository.R
import com.brianperin.githubrepository.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Our api client implementation
 * Use apollo with okhttp for adding auth header
 * and anything else we want for all requests
 * This must be set up at the application level
 * before we try to make api calls
 */
object ApolloClientImpl {

    lateinit var apiClient: ApolloClient

    fun setup(context: Context) {

        val cacheFactory = LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build())
        val bearer = context.getString(R.string.auth_header_bearer)
        val prefix = context.getString(R.string.auth_header_value_prefix)
        val suffix = context.getString(R.string.auth_header_value_suffix)

        val token = bearer + " " + prefix + "_" + suffix

        val graphApiEndpoint = context.getString(R.string.graph_api_endpoint)

        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addNetworkInterceptor { chain ->

                val original = chain.request()
                val builder = original.newBuilder()

                builder.header(Constants.ACCEPT, Constants.APPLICATION_JSON)
                builder.header(Constants.ACCEPT_ENCODING, Constants.APPLICATION_JSON)
                builder.header(Constants.AUTHORIZATION, token)

                builder.method(original.method, original.body)

                val originalRequest = chain.proceed(builder.build())
                val newRequest = originalRequest.newBuilder()

                newRequest.build()
            }
            .build()

        apiClient = ApolloClient.builder()
            .serverUrl(graphApiEndpoint)
            .normalizedCache(cacheFactory)
            .okHttpClient(okHttpClient)
            .build()
    }
}
