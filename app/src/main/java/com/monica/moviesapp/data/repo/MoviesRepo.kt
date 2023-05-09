package com.monica.moviesapp.data.repo

import com.monica.moviesapp.data.helpers.Resource
import com.monica.moviesapp.data.models.MovieListItem
import com.monica.moviesapp.data.models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {
    suspend fun getMovies(): Flow<Resource<List<MovieListItem>>>
    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieResponse>>

}
