package com.monica.moviesapp.data.remote

import com.monica.moviesapp.data.models.MovieResponse
import com.monica.moviesapp.data.models.MoviesResponse


interface MoviesRemoteSource {
    suspend fun getMoviesList(): MoviesResponse?
    suspend fun getMovieDetails(movieId: Int): MovieResponse?

}










