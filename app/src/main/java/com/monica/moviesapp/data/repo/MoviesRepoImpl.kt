package com.monica.moviesapp.data.repo

import com.monica.moviesapp.data.helpers.FailureData
import com.monica.moviesapp.data.helpers.Resource
import com.monica.moviesapp.data.helpers.SchedulerProvider
import com.monica.moviesapp.data.models.MovieListItem
import com.monica.moviesapp.data.models.MovieResponse
import com.monica.moviesapp.data.remote.MoviesRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class MoviesRepoImpl(
    private val moviesRemoteSource: MoviesRemoteSource,
    private val schedulerProvider: SchedulerProvider,
) : MoviesRepo {

    override suspend fun getMovies(): Flow<Resource<List<MovieListItem>>> {
        return flow {
            try {
                val moviesResponse = moviesRemoteSource.getMoviesList()
                if (moviesResponse != null && moviesResponse.results.isNotEmpty())
                    emit(Resource.Success(moviesResponse.results))
                else
                    emit(Resource.Failure(FailureData(message = "No Data Found")))

            } catch (ex: Exception) {
                emit(Resource.Failure(FailureData(message = ex.toString())))
            }
        }.flowOn(schedulerProvider.io())
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieResponse>> {
        return flow {
            try {
                val moveResponse = moviesRemoteSource.getMovieDetails(movieId)
                if (moveResponse != null)
                    emit(Resource.Success(moveResponse))
                else
                    emit(Resource.Failure(FailureData(message = "No Data Found")))

            } catch (ex: Exception) {
                emit(Resource.Failure(FailureData(message = ex.toString())))
            }
        }.flowOn(schedulerProvider.io())
    }
}