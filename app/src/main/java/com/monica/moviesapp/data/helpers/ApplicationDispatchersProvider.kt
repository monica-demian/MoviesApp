package com.monica.moviesapp.data.helpers

import kotlinx.coroutines.Dispatchers

class ApplicationDispatchersProvider : SchedulerProvider {
    override fun io() = Dispatchers.IO
    override fun ui() = Dispatchers.Main
    override fun default() = Dispatchers.Default
}
