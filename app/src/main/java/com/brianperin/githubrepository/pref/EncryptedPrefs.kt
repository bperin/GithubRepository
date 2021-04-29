package com.brianperin.githubrepository.pref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.brianperin.githubrepository.util.Constants

/**
 * This class doesn't make a whole lot of sense since we
 * have a static api key but in a full fledged application
 * we would get our api key through oAuth or another mechanism and save
 */
object EncryptedPrefs {

    lateinit var sharedPreferences: EncryptedSharedPreferences

    fun setup(context: Context) {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        sharedPreferences = EncryptedSharedPreferences.create(
            "encrypted_prefs", masterKeyAlias, context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    fun setApiKey(key: String) {

        sharedPreferences
            .edit()
            .putString(Constants.API_KEY, key)
            .apply()
    }

    fun getApiKey(): String? {
        return sharedPreferences.getString(Constants.API_KEY, null)
    }
}