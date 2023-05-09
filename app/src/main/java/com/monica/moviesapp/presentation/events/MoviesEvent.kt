package com.monica.moviesapp.presentation.events

import com.monica.moviesapp.data.models.MovieListItem

sealed class MoviesEvent : BaseEvents() {
    class SetMoviesList(val moviesList: List<MovieListItem>) : MoviesEvent()
}