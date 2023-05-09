package com.monica.moviesapp.presentation.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.monica.moviesapp.R
import com.monica.moviesapp.data.models.MovieListItem
import com.monica.moviesapp.databinding.MovieItemBinding


class MoviesAdapter(
    private var moviesList: List<MovieListItem>,
    private var onMovieClicked: ((movieId: Int) -> Unit)
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val baseImageUrl = "https://image.tmdb.org/t/p/original/"

    inner class ViewHolder(internal val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(moviesList[position]) {
                binding.tvName.text = this.original_title
                binding.tvReleaseData.text = this.release_date
                Glide
                    .with(itemView.context)
                    .load("$baseImageUrl${this.poster_path}")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.ivMovie)
                binding.root.setOnClickListener {
                    onMovieClicked(this.id)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}