package com.brianperin.githubrepository.adapter

import com.brianperin.githubrepository.R
import com.idanatz.oneadapter.external.modules.EmptinessModule

class EmptinessModuleImpl : EmptinessModule() {
    init {
        config {
            layoutResource = R.layout.empty_users
        }
    }
}