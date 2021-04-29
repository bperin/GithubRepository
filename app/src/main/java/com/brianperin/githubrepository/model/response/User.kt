package com.brianperin.githubrepository.model.response

import com.idanatz.oneadapter.external.interfaces.Diffable

data class User(
    val avatarUrl: String,
    val login: String,
    val repositories: Int,
    val cursor: String
) : Diffable {
    override val uniqueIdentifier: Long
        get() = login.hashCode().toLong()

    override fun areContentTheSame(other: Any): Boolean {
        return true
    }
}