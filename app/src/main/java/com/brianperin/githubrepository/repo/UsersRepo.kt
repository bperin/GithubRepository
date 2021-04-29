package com.brianperin.githubrepository.repo

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.network.ApolloClientImpl
import com.brianperin.githubrepository.network.BaseDataSource
import com.brianperin.githubrepository.util.Constants

class UsersRepo : BaseDataSource() {

    suspend fun getUsers(query: String) = getQueryResult { ApolloClientImpl.apiClient.query(GetUsersQuery(query, Constants.PAGE_SIZE)).await() }
    suspend fun getUsers(query: String, after: Input<String>) = getQueryResult { ApolloClientImpl.apiClient.query(GetUsersQuery(query, Constants.PAGE_SIZE, after)).await() }
}