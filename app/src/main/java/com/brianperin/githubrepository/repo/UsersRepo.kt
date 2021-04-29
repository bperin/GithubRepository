package com.brianperin.githubrepository.repo

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.network.ApolloClientImpl
import com.brianperin.githubrepository.network.BaseDataSource

class UsersRepo : BaseDataSource() {

    companion object {
        const val FIRST = 20
    }

    suspend fun getUsers(query: String) = getQueryResult { ApolloClientImpl.apiClient.query(GetUsersQuery(query, FIRST)).await() }
    suspend fun getUsers(query: String, after: Input<String>) = getQueryResult { ApolloClientImpl.apiClient.query(GetUsersQuery(query, FIRST, after)).await() }
}