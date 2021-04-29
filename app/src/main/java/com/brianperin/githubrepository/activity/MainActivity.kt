package com.brianperin.githubrepository.activity

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.brianperin.githubrepository.R
import com.brianperin.githubrepository.fragment.UsersFragment
import com.brianperin.githubrepository.util.Constants
import com.brianperin.githubrepository.viewmodel.UsersViewModel
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import timber.log.Timber

/**
 * Main activity doesn't do much except instantiate the users view model
 * here we can navigate fragments manually if we dont use jetpack navigation
 */
class MainActivity : AppCompatActivity() {

    private val usersViewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showUsersFragment()
        }

    }

    /**
     * Init our main fragment with needed permission todo handle non permission access
     */
    private fun showUsersFragment() = runWithPermissions(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    ) {

        val usersFragment = UsersFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, usersFragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(Constants.TIMBER).d("main activity destroyed")
    }
}