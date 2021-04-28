package com.brianperin.githubrepository.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.network.ApolloImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class UsersViewModel : ViewModel() {

    fun getUsers() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resp =
                    ApolloImpl.apolloClient.query(GetUsersQuery("brian", 10)).toDeferred().await()
                Timber.tag("apollo").d(resp.toString())
            } catch (e: ApolloException) {
                e.printStackTrace()
            }
        }
    }
}