package com.brianperin.githubrepository.adapter

import com.brianperin.githubrepository.R
import com.brianperin.githubrepository.util.Constants
import com.brianperin.githubrepository.viewmodel.UsersViewModel
import com.idanatz.oneadapter.external.modules.PagingModule
import timber.log.Timber

class PagingModuleImpl(usersViewModel: UsersViewModel) : PagingModule() {
    init {
        config {
            layoutResource = R.layout.load_more // can be some spinner animation
            visibleThreshold = 3 // invoke onLoadMore 3 items before the end
        }
        onLoadMore { currentPage ->
            Timber.tag(Constants.TIMBER).d("load more")
            if (usersViewModel.hasNext) {
                usersViewModel.next()
            }
        }
    }
}