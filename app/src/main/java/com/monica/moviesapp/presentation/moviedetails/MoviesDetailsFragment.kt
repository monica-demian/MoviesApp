package com.monica.moviesapp.presentation.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.monica.moviesapp.R
import com.monica.moviesapp.data.models.MovieResponse
import com.monica.moviesapp.databinding.FragmentMoviesDetailsBinding
import com.monica.moviesapp.presentation.events.BaseEvents
import com.monica.moviesapp.presentation.events.MovieEvents
import com.monica.moviesapp.presentation.events.MoviesEvent
import com.monica.moviesapp.presentation.movies.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailsFragment : Fragment() {

    private val args: MoviesDetailsFragmentArgs by navArgs()
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var fragmentMovieDetailsBinding: FragmentMoviesDetailsBinding? = null
    private val baseImageUrl = "https://image.tmdb.org/t/p/w500/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        val binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        fragmentMovieDetailsBinding = binding
        setListeners()
        return binding.root
    }
    private fun setListeners() {
        moviesViewModel.movieDetailsSingleLiveEvent.observe(viewLifecycleOwner) { movieEvent ->
            when (movieEvent) {
                is MovieEvents.SetMovieDetails -> bindMovieDetails(movieEvent.moveResponse)
            }
        }
    }
    private fun bindMovieDetails(movieResponse: MovieResponse){
        fragmentMovieDetailsBinding?.ivMovie?.let {
            Glide
                .with(requireView())
                .load("$baseImageUrl${movieResponse.backdrop_path}")
                .placeholder(R.drawable.ic_launcher_background)
                .into(it)

        }
        fragmentMovieDetailsBinding?.tvName?.text=movieResponse.title
        fragmentMovieDetailsBinding?.tvOverview?.text=movieResponse.overview
        fragmentMovieDetailsBinding?.tvReleaseData?.text=movieResponse.release_date

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
    private fun setup() {
        moviesViewModel.getMovieDetailsFromAPI(args.movieId)
    }

}