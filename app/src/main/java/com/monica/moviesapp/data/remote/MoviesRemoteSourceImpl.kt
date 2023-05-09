package com.monica.moviesapp.data.remote

import com.monica.moviesapp.BuildConfig
import com.monica.moviesapp.data.models.MovieListItem
import com.monica.moviesapp.data.models.MovieResponse
import com.monica.moviesapp.data.models.MoviesResponse


class MoviesRemoteSourceImpl(private val moviesApi: MoviesApi) : MoviesRemoteSource {

    override suspend fun getMoviesList(): MoviesResponse {
        return moviesApi.getMoviesList(
            apikey = BuildConfig.API_URL,
            sort = "popularity.desc",
            language = "en-US",
            isIncludeAdult = false,
            isIncludeVideo = false
        )
    }

    override suspend fun getMovieDetails(movieId: Int): MovieResponse {
        return moviesApi.getMoviesDetails(
            movieId=movieId,
            apikey = BuildConfig.API_URL,
            language = "en-US",
        )
    }
}
