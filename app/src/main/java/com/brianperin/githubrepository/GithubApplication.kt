package com.brianperin.githubrepository

import android.app.Application
import timber.log.Timber

class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}