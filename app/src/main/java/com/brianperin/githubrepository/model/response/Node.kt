package com.brianperin.githubrepository.model.response

data class Node(
    val avatarUrl: String,
    val login: String,
    val repositories: Int,
    val cursor : String
)