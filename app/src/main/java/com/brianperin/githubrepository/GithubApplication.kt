package com.brianperin.githubrepository

import android.app.Application
import com.brianperin.githubrepository.network.ApolloClientImpl
import com.brianperin.githubrepository.pref.EncryptedPrefs
import timber.log.Timber

class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        ApolloClientImpl.setup(this)
        EncryptedPrefs.setup(this)
    }
}