package com.monica.moviesapp.presentation.events

import com.monica.moviesapp.data.models.MovieResponse

sealed class MovieEvents {
    class SetMovieDetails(val moveResponse: MovieResponse) : MovieEvents()
}