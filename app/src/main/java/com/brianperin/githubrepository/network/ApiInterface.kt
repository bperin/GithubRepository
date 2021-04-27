package com.brianperin.githubrepository.network

import com.brianperin.githubrepository.model.response.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Interface with url pointing to repo
 */
interface ApiInterface {

    @GET("/users/{userName}")
    suspend fun getUsers(@Path("userName") userName: String): Response<List<User>>
}