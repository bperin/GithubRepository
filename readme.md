# Github user search

application that allows you to query githubs public api using graphql instead of regular REST.
GraphQl is the optimal choice because the data required, getting a list of users and showing the amount of repos they have would take two API calls per user.
With graphQl we can optimize the search for just the data we need and impelment it with https://www.apollographql.com/docs/android/

## Summary

The main focus here was to build an application that can scale, there are abstract classes that can be extended for other queries if we wanted in the future
for example https://github.com/bperin/GithubRepository/blob/master/app/src/main/java/com/brianperin/githubrepository/network/Result.kt is a generic result class feeding data
back to our repositories which again extend a generic https://github.com/bperin/GithubRepository/blob/master/app/src/main/java/com/brianperin/githubrepository/network/BaseDataSource.kt class

The overall idea is most parts of the application dont know about others making sure we have a clear seperation of concerns, this is what was mostly focused on.

I opted for using a fragment instead of just an activity to show how again a full fledged application can communicate between activities and fragments, ie master->detail

Apollo generates queries based off of graphQl statements as seen here https://github.com/bperin/GithubRepository/blob/master/app/src/main/graphql/com/brianperin/githubrepository/GetUsers.graphql
To generate the necessary classes after first gradle sync you'll need to do the gradle task 

## Setup, apollo android kotlin data classes
```
 ./gradlew generateApolloSources

```
this will generate the necessary classes and api calls however we dont necessarily want what Apollo generates, the classes dont understand what fields are nullable and their types

we can leverage kotlin extension functions to convert the apollo respopne data to a more suitable kotlin data class

https://github.com/bperin/GithubRepository/blob/master/app/src/main/java/com/brianperin/githubrepository/util/Extensions.kt

Apollo has some bugs interacting with OkHttp which took a while to figure out, lazy instantiation of objects leads to very slow responses on first query,
this was fixed in the setup method for the ApolloClientImpl which is configured on app launch.

## Caching and optimization

We've also implemented two caches, one for responses from github in the form of LRU cache to avoid making repeating queries to github, a 10 mb LRU cache is created
https://github.com/apollographql/apollo-android/blob/main/apollo-normalized-cache/src/jvmMain/kotlin/com/apollographql/apollo/cache/normalized/lru/LruNormalizedCache.kt

Second is in the Glide image cache which will read bitmaps from memory, a trade off between saving on disk, but will load faster https://github.com/bumptech/glide

## Adapter / list
The adapter is a 3rd party library https://github.com/ironSource/OneAdapter which eliminates a lot of boiler plate code for a typical https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter adapter. We also can implement empty state, 0 results and pagination which is critical for loading more items, I chose using this for sake of time and simplicity focusing more on architecture overall.
