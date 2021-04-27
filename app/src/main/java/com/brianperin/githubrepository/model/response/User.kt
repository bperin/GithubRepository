package com.brianperin.githubrepository.model.response

import com.google.gson.annotations.SerializedName
import java.util.*


/**
 * Data object representing a single employee
 */
data class User(

        @SerializedName("id")
        val id: Long,

        @SerializedName("login")
        val username: String,

        @SerializedName("avatar_url")
        val avatar: String?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("company")
        val company: String?,

        @SerializedName("email")
        val email: String?,

        @SerializedName("location")
        val location: String?,

        @SerializedName("bio")
        val bio: String?,

        @SerializedName("public_repos")
        val repos: Int,

        @SerializedName("followers")
        val followers: Int,

        @SerializedName("following")
        val following: Int,

        @SerializedName("created_at")
        val createdAt: Date,
) {}