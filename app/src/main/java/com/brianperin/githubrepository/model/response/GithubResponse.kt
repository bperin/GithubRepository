package com.brianperin.githubrepository.model.response

data class GithubResponse(
    val data: Data
) {
    data class Data(
        val search: Search
    ) {
        data class Search(
            val edges: List<Edge>
        ) {
            data class Edge(
                val node: Node
            ) {
                data class Node(
                    val avatarUrl: String,
                    val id: String,
                    val login: String,
                    val name: String?,
                    val repositories: Repositories
                ) {
                    data class Repositories(
                        val totalCount: Int
                    )
                }
            }
        }
    }
}
