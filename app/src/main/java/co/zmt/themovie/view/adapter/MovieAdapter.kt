package co.zmt.themovie.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zmt.themovie.databinding.ItemMovieBinding
import co.zmt.themovie.helper.Api
import co.zmt.themovie.helper.loadFromUrl
import co.zmt.themovie.model.local.db.movie.LikedMovie
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre

class MovieAdapter(private val event: MovieItemClickEvent) :
    ListAdapter<MovieWithMovieGenre, MovieViewHolder>(
        object : DiffUtil.ItemCallback<MovieWithMovieGenre>() {
            override fun areItemsTheSame(
                oldItem: MovieWithMovieGenre,
                newItem: MovieWithMovieGenre
            ): Boolean {
                return oldItem.movie.id == newItem.movie.id
            }

            override fun areContentsTheSame(
                oldItem: MovieWithMovieGenre,
                newItem: MovieWithMovieGenre
            ): Boolean {
                return oldItem.movie.title == newItem.movie.title
            }
        }

    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding.root, event)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class MovieViewHolder(val view: View, private val event: MovieItemClickEvent) :
    RecyclerView.ViewHolder(view) {
    private var currentItem: MovieWithMovieGenre? = null
    private val binding = ItemMovieBinding.bind(view)

    init {
        binding.root.setOnClickListener {
            currentItem?.apply {
                event.itemClick(this)
            }
        }
    }

    fun bind(item: MovieWithMovieGenre) {
        currentItem = item
        binding.itemPosterTitle.text = item.movie.title
        binding.itemPosterPost.loadFromUrl(
            Api.getPosterPath(item.movie.posterPath)
        )
    }
}

interface MovieItemClickEvent {
    fun itemClick(item: MovieWithMovieGenre)
}