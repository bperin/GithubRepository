package com.brianperin.githubrepository

import androidx.annotation.UiThread
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.brianperin.githubrepository.fragment.UsersFragment
import com.brianperin.githubrepository.model.response.User
import com.brianperin.githubrepository.network.ApolloClientImpl
import com.brianperin.githubrepository.network.Result
import com.brianperin.githubrepository.viewmodel.UsersViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UsersInstrumentationClass() {


    @Before
    fun setUp() {
        ApolloClientImpl.setup(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun queryReturnsOk() = runBlocking {
        val response = ApolloClientImpl.apiClient.query(GetUsersQuery("brian", 20)).await()
        assert(!response.hasErrors())
    }

    @Test
    fun queryReturnsErrorIfFirst0() = runBlocking {
        val response = ApolloClientImpl.apiClient.query(GetUsersQuery("brian", -1)).await()
        assert(response.hasErrors())
    }

    @Test
    fun queryReturnsErrorIfCursorInvalid() = runBlocking {
        val input: Input<String> = Input.optional("0x")
        val response = ApolloClientImpl.apiClient.query(GetUsersQuery("brian", 20, input)).await()
        assert(response.hasErrors())
    }

    @Test
    fun queryReturnsOkAndNext() = runBlocking {
        val response = ApolloClientImpl.apiClient.query(GetUsersQuery("brian", 20)).await()
        assert(!response.hasErrors())
    }



//
//    @After
//    fun tearDown() {
//
//    }
}