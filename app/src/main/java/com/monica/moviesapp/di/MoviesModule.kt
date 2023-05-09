package com.monica.moviesapp.di

import com.monica.moviesapp.data.helpers.SchedulerProvider
import com.monica.moviesapp.data.remote.MoviesApi
import com.monica.moviesapp.data.remote.MoviesRemoteSource
import com.monica.moviesapp.data.remote.MoviesRemoteSourceImpl
import com.monica.moviesapp.data.repo.MoviesRepo
import com.monica.moviesapp.data.repo.MoviesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit


@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
object MoviesModule {
    @Provides
    internal fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    internal fun provideMoviesRemoteSource(
        moviesApi: MoviesApi,
    ): MoviesRemoteSource {
        return MoviesRemoteSourceImpl(moviesApi)
    }

    @Provides
    fun provideMoviesRepo(
        moviesRemoteSource: MoviesRemoteSource,
        schedulerProvider: SchedulerProvider,
    ): MoviesRepo {
        return MoviesRepoImpl(
            moviesRemoteSource,
            schedulerProvider,
        )
    }

}