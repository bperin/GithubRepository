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

        viewModelScope.launch(Dispatchers.IO) {
            usersLiveData.postValue(Result(Result.Status.LOADING, null, null))
            try {
                val result: Result<GetUsersQuery.Data>

                //get first
                result = if (!hasNext && lastCursor == null) {
                    usersRepo.getUsers(query!!)
                } else {//next
                    val input: Input<String> = Input.optional(lastCursor)
                    usersRepo.getUsers(query!!, input)
                }
                if (result.status == Result.Status.SUCCESS) {

                    val users = result.toDataArray()

                    //if we dont have enough items dont allow pagination
                    if (users.isNotEmpty()) {

                        usersLiveData.postValue(Result(Result.Status.SUCCESS, users, null))

                        if (users.size == Constants.PAGE_SIZE) {
                            val lastUser = users.last()
                            lastCursor = lastUser.cursor
                            hasNext = true
                        } else {
                            clear()
                        }

                    } else {
                        usersLiveData.postValue(Result(Result.Status.EMPTY, emptyList(), null))
                        clear()
                    }

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
        hasNext = false
    }

    fun next() {
        fetch()
    }
}