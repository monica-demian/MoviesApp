package com.monica.moviesapp.data.remote

import com.monica.moviesapp.data.models.MovieResponse
import com.monica.moviesapp.data.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    companion object {
        const val URL_MOVIES_LIST = "discover/movie?"
        const val API_KEY = "api_key"
        const val MOVIE_ID = "id"
        const val LANGUAGE = "language"
        const val SORT = "sort_by"
        const val INCLUDE_ADULT = "include_adult"
        const val INCLUDE_VIDEO = "include_video"
        const val URL_MOVIE_DETAILS="movie/{id}?"
    }

    @GET(URL_MOVIES_LIST)
    suspend fun getMoviesList(
        @Query(API_KEY) apikey: String,
        @Query(LANGUAGE) language: String,
        @Query(SORT) sort: String,
        @Query(INCLUDE_ADULT) isIncludeAdult: Boolean,
        @Query(INCLUDE_VIDEO) isIncludeVideo: Boolean
    ): MoviesResponse

    @GET(URL_MOVIE_DETAILS)
    suspend fun getMoviesDetails(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apikey: String,
        @Query(LANGUAGE) language: String
    ): MovieResponse
}