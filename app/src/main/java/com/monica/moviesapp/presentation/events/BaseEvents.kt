package com.monica.moviesapp.presentation.events

sealed class BaseEvents {
    class ShowErrorMessage(val errorMessage: String) : MoviesEvent()
    class HandleLoading(val isLoading: Boolean) : MoviesEvent()
}