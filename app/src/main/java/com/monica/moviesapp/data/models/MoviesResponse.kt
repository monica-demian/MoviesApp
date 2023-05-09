package com.monica.moviesapp.data.models

data class MoviesResponse(
    val page: Int,
    val results: List<MovieListItem>,
    val total_pages: Int,
    val total_results: Int
)