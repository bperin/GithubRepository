package com.brianperin.githubrepository.util

import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.model.response.User
import com.brianperin.githubrepository.network.Result


/**
 * Mutate our apollo object into regular data class array
 */
fun Result<GetUsersQuery.Data>.toDataArray(): List<User> {

    val list: MutableList<User> = mutableListOf<User>()
    val edges = this.data?.search?.edges
    edges?.let { edge ->
        edge.forEach {
            val cursor = it!!.cursor
            val graphUser = it.node?.asUser
            val node = User(graphUser?.avatarUrl as String, graphUser.login, graphUser.repositories.totalCount, cursor)
            list.add(node)
        }
    }
    return list.toList()
}
