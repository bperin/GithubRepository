package com.brianperin.githubrepository.repo

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.network.ApolloClientImpl
import com.brianperin.githubrepository.network.BaseDataSource
import com.brianperin.githubrepository.util.Constants

/**
 * Rep layer, we can decide to cache respones here but okhttp is caching them now
 * also different methods depending on if we're fetching more data on the same query
 */
class UsersRepo : BaseDataSource() {

    suspend fun getUsers(query: String) = getQueryResult { ApolloClientImpl.apiClient.query(GetUsersQuery(query, Constants.PAGE_SIZE)).await() }
    suspend fun getUsers(query: String, after: Input<String>) = getQueryResult { ApolloClientImpl.apiClient.query(GetUsersQuery(query, Constants.PAGE_SIZE, after)).await() }
}