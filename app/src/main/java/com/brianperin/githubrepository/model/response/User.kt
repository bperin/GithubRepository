package com.brianperin.githubrepository.model.response

import com.idanatz.oneadapter.external.interfaces.Diffable

/**
 * Our main class we're going to represent a user it doesnt
 * map 100% to the github User object, we are just going to flatten
 * it so everything is in one class, repo count and cursor are not in the github user obj
 * implements diffable for hooking up to our adapter
 */
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