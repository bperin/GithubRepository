package com.brianperin.githubrepository.network

import com.apollographql.apollo.ApolloClient

object ApolloImpl {

    var apolloClient = ApolloClient.builder()
        .serverUrl("https://api.github.com/graphql")
        .build()


//    scope.launch
//    {
//        val response = try {
//            apolloClient.query(LaunchDetailsQuery(id = "83")).toDeferred().await()
//        } catch (e: ApolloException) {
//            // handle protocol errors
//            return@launch
//        }
//
//        val launch = response.data?.launch
//        if (launch == null || response.hasErrors()) {
//            // handle application errors
//            return@launch
//        }
//
//        // launch now contains a typesafe model of your data
//        println("Launch site: ${launch.site}")
}