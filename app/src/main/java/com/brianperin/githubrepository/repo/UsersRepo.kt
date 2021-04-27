package com.brianperin.githubrepository.repo

import com.brianperin.githubrepository.network.ApiClient
import com.brianperin.githubrepository.network.BaseDataSource

class UsersRepo() : BaseDataSource() {
    suspend fun getUsers(userName: String) = getResult { ApiClient.apiService.getUsers(userName) }
}