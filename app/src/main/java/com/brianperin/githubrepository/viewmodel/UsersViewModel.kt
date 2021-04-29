package com.brianperin.githubrepository.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.model.response.User
import com.brianperin.githubrepository.network.Result
import com.brianperin.githubrepository.repo.UsersRepo
import com.brianperin.githubrepository.util.Constants
import com.brianperin.githubrepository.util.toDataArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val usersRepo = UsersRepo()
    private var query: String? = null
    private var lastCursor: String? = null
    val usersLiveData = MutableLiveData<Result<List<User>>>()
    var hasNext = false

    fun search(query: String) {
        this.query = query
        this.lastCursor = null
        fetch()
    }

    private fun fetch() {

        if (query == null) {
            usersLiveData.value = Result(Result.Status.ERROR, null, "invalid query")
            clear()
            return
        }
        usersLiveData.value = Result(Result.Status.LOADING, null, null)

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val result: Result<GetUsersQuery.Data>
                if (hasNext && lastCursor != null) {
                    result = usersRepo.getUsers(query!!)
                } else {
                    result = usersRepo.getUsers(query!!,)
                }


                if (result.status == Result.Status.SUCCESS) {

                    val users = result.toDataArray()

                    if (users.isNotEmpty() && users.size == Constants.PAGE_SIZE) {
                        val lastUser = users.last()
                        lastCursor = lastUser.cursor
                        hasNext = true
                    } else {
                        clear()
                    }
                    usersLiveData.postValue(Result(Result.Status.SUCCESS, users, null))

                } else if (result.status == Result.Status.ERROR) {
                    usersLiveData.postValue(Result(Result.Status.ERROR, null, result.message))
                    clear()
                }

            } catch (e: Exception) {
                usersLiveData.postValue(Result(Result.Status.ERROR, null, e.message))
                clear()
            }
        }

    }

    fun clear() {
        query = null
        lastCursor = null
    }

    fun next() {
        fetch()
    }
}