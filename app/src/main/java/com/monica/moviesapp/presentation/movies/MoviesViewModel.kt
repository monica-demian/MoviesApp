package com.monica.moviesapp.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monica.moviesapp.data.helpers.FailureData
import com.monica.moviesapp.data.helpers.Resource
import com.monica.moviesapp.data.helpers.SingleLiveEvent
import com.monica.moviesapp.data.models.MovieListItem
import com.monica.moviesapp.data.models.MovieResponse
import com.monica.moviesapp.data.repo.MoviesRepo
import com.monica.moviesapp.presentation.events.BaseEvents
import com.monica.moviesapp.presentation.events.MovieEvents
import com.monica.moviesapp.presentation.events.MoviesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepo,
) : ViewModel() {
    private val _moviesSingleLiveEvent: SingleLiveEvent<MoviesEvent> =
        SingleLiveEvent()

    val moviesSingleLiveEvent: SingleLiveEvent<MoviesEvent> =
        _moviesSingleLiveEvent

    private val _movieDetailsSingleLiveEvent: SingleLiveEvent<MovieEvents> =
        SingleLiveEvent()

    val movieDetailsSingleLiveEvent: SingleLiveEvent<MovieEvents> =
        _movieDetailsSingleLiveEvent


    fun getMoviesFromAPI() {
        try {
            viewModelScope.launch {
                repo.getMovies().collect { result ->
                    when (result) {
                        is Resource.Loading -> handleLoading(isLoading = true)
                        is Resource.Success<List<MovieListItem>> -> result.data?.let {
                            handleMoviesList(
                                it
                            )
                        }
                        is Resource.Failure -> result.failureData?.let { handleError(it) }

                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            handleError(FailureData(message = exception.toString()))
        }
    }

    fun getMovieDetailsFromAPI(movieId: Int) {
        try {
            viewModelScope.launch {
                repo.getMovieDetails(movieId).collect { result ->
                    when (result) {
                        is Resource.Loading -> handleLoading(isLoading = true)
                        is Resource.Success<MovieResponse> -> result.data?.let {
                            handleMovieDetails(
                                it
                            )
                        }
                        is Resource.Failure -> result.failureData?.let { handleError(it) }

                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            handleError(FailureData(message = exception.toString()))
        }
    }

    private fun handleError(failure: FailureData) {
        Log.e("monica", failure.message.toString())
        handleLoading(isLoading = false)
        _moviesSingleLiveEvent.value = failure.message?.let { BaseEvents.ShowErrorMessage(it) }
    }

    private fun handleMoviesList(moviesList: List<MovieListItem>) {
        handleLoading(isLoading = false)
        _moviesSingleLiveEvent.value = MoviesEvent.SetMoviesList(moviesList)
    }

    private fun handleMovieDetails(movieResponse: MovieResponse) {
        handleLoading(isLoading = false)
        _movieDetailsSingleLiveEvent.value = MovieEvents.SetMovieDetails(movieResponse)
    }

    private fun handleLoading(isLoading: Boolean) {
        _moviesSingleLiveEvent.value = BaseEvents.HandleLoading(isLoading)
    }


}