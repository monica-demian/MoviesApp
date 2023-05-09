package com.monica.moviesapp.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.monica.moviesapp.data.helpers.hide
import com.monica.moviesapp.data.helpers.show
import com.monica.moviesapp.data.models.MovieListItem
import com.monica.moviesapp.databinding.FragmentMoviesBinding
import com.monica.moviesapp.presentation.events.BaseEvents
import com.monica.moviesapp.presentation.events.MoviesEvent
import com.monica.moviesapp.presentation.movies.adapters.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private var fragmentMoviesBinding: FragmentMoviesBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)
        fragmentMoviesBinding = binding
        setListeners()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel.getMoviesFromAPI()
    }

    private fun setListeners() {
        moviesViewModel.moviesSingleLiveEvent.observe(/* owner = */ viewLifecycleOwner) { moviesEvent ->
            when (moviesEvent) {
                is BaseEvents.HandleLoading -> handleProgressBarVisibility(moviesEvent.isLoading)
                is BaseEvents.ShowErrorMessage -> showSnackBar(moviesEvent.errorMessage)
                is MoviesEvent.SetMoviesList -> setupMoviesAdapter(moviesEvent.moviesList)

            }
        }
    }

    private fun showSnackBar(errorMessage: String) {
        Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupMoviesAdapter(moviesList: List<MovieListItem>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        fragmentMoviesBinding?.rvMoviesList?.layoutManager = layoutManager
        moviesAdapter = MoviesAdapter(moviesList) { movieId ->
            openMovieDetailsScreen(movieId)
        }
        fragmentMoviesBinding?.rvMoviesList?.adapter = moviesAdapter
    }

    private fun openMovieDetailsScreen(movieId: Int) {
        val action = MoviesFragmentDirections.actionOpenMoviesDetails(movieId)
        findNavController().navigate(action)
    }

    private fun handleProgressBarVisibility(isVisible: Boolean) {
        if (isVisible) {
            fragmentMoviesBinding?.progressBar?.show()
        } else {
            fragmentMoviesBinding?.progressBar?.hide()
        }

    }

    override fun onDestroyView() {
        fragmentMoviesBinding = null
        super.onDestroyView()
    }

}