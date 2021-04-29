package com.brianperin.githubrepository.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.model.response.User
import com.brianperin.githubrepository.network.Result
import com.brianperin.githubrepository.repo.UsersRepo
import com.brianperin.githubrepository.util.toDataArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val usersRepo = UsersRepo()
    private var query: String? = null
    private var lastCursor: String? = null
    val usersLiveData = MutableLiveData<Result<List<User>>>()

    fun search(query: String) {
        this.query = query
        this.lastCursor = null

        fetch()

    }

    private fun fetch() {

        if (query == null) {
            usersLiveData.value = Result(Result.Status.ERROR, null, "invalid query")
            return
        }
        usersLiveData.value = Result(Result.Status.LOADING, null, null)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result: Result<GetUsersQuery.Data> = usersRepo.getUsers(query!!)

                if (result.status == Result.Status.SUCCESS) {
                    val users = result.toDataArray()
                    if (users.isNotEmpty()) {
                        lastCursor = users.lastOrNull()?.cursor
                    } else {
                        lastCursor = null
                    }
                    usersLiveData.postValue(Result(Result.Status.SUCCESS, users, null))
                }

            } catch (e: Exception) {
                usersLiveData.postValue(Result(Result.Status.ERROR, null, e.message))
            }
        }

    }

    fun clear() {
        query = null
        lastCursor = null
    }

    fun onNext() {

    }
}