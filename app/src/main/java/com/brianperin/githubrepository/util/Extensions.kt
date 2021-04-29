package com.brianperin.githubrepository.util

import com.brianperin.githubrepository.GetUsersQuery
import com.brianperin.githubrepository.model.response.Node
import com.brianperin.githubrepository.network.Result
import okhttp3.internal.toImmutableList
import java.util.Collections.emptyList


/**
 * Mutate our apollo object into regular data class array
 */
fun Result<GetUsersQuery.Data>.toDataArray(): List<Node> {

    val list: MutableList<Node> = emptyList()
    val edges = this.data?.search?.edges
    edges?.let { edge ->
        edge.forEach {
            val cursor = it!!.cursor
            val graphUser = it.node?.asUser
            val node = Node(graphUser?.avatarUrl as String, graphUser.login, graphUser.repositories.totalCount, cursor)
            list.add(node)
        }
    }
    return list.toImmutableList()
}
