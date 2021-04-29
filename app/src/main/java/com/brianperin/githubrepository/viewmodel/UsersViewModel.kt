package com.brianperin.githubrepository.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.network.Result
import com.brianperin.githubrepository.repo.UsersRepo
import com.brianperin.githubrepository.util.toDataArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val usersRepo = UsersRepo()


    var userName: String? = null
    var after: String? = null

    fun search(query: String) {
        this.userName = query
        this.after = null

//        usersRepo.getUsers(userName)
    }

    fun getUsers() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result: Result<GetUsersQuery.Data> = usersRepo.getUsers("brian")

                if (result.status == Result.Status.SUCCESS) {
                    val users = result.toDataArray()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clear() {

    }

    fun onNext() {

    }
}