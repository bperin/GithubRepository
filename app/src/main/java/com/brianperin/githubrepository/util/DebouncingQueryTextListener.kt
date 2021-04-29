package com.brianperin.githubrepository.util

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*

/**
 * Helper class to debounce input to the search query
 * Use coroutine job so if a job is started and there is new input
 * the job will cancel and wait delay the next query
 */
internal class DebouncingQueryTextListener(lifecycle: Lifecycle, private val onDebouncingQueryTextChange: (String?) -> Unit) : SearchView.OnQueryTextListener, LifecycleObserver {

    private var debouncePeriod: Long = 500
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        searchJob?.cancel()
    }
}