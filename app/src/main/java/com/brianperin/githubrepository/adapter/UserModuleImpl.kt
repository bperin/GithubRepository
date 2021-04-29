package com.brianperin.githubrepository.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.brianperin.githubrepository.R
import com.brianperin.githubrepository.model.response.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.idanatz.oneadapter.external.modules.ItemModule

/**
 * Helper class that OneAdapter uses to basically do all the under the
 * hood work of a recycler view adapter in terms of viewholder creation and
 * binding our data model to it
 */
class UserModuleImpl(context: Context) : ItemModule<User>() {
    init {
        config {
            layoutResource = R.layout.list_user
        }
        onBind { model, viewBinder, metadata ->
            val login = viewBinder.findViewById<TextView>(R.id.tvLogin)
            val repos = viewBinder.findViewById<TextView>(R.id.tvRepoCount)
            val avatar = viewBinder.findViewById<ImageView>(R.id.ivAvatar)

            login.text = model.login
            repos.text = model.repositories.toString()

            context?.let {
                Glide.with(it)
                    .load(model.avatarUrl)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .into(avatar);
            }
        }
        onUnbind { model, viewBinder, metadata ->
            // unbind logic like stop animation, release webview resources, etc.
        }
    }
}
