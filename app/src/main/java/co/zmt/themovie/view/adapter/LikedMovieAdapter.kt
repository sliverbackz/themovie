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

class LikedMovieAdapter(private val event: LikedMovieItemClickEvent) :
    ListAdapter<LikedMovie, LikedMovieViewHolder>(
        object : DiffUtil.ItemCallback<LikedMovie>() {
            override fun areItemsTheSame(
                oldItem: LikedMovie,
                newItem: LikedMovie
            ): Boolean {
                return oldItem.movie.id == newItem.movie.id
            }

            override fun areContentsTheSame(
                oldItem: LikedMovie,
                newItem: LikedMovie
            ): Boolean {
                return oldItem.movie.title == newItem.movie.title
            }
        }

    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikedMovieViewHolder(binding.root, event)
    }

    override fun onBindViewHolder(holder: LikedMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class LikedMovieViewHolder(val view: View, private val event: LikedMovieItemClickEvent) :
    RecyclerView.ViewHolder(view) {
    private var currentItem: LikedMovie? = null
    private val binding = ItemMovieBinding.bind(view)

    init {
        binding.root.setOnClickListener {
            currentItem?.apply {
                event.itemClick(this)
            }
        }
    }

    fun bind(item: LikedMovie) {
        currentItem = item
        binding.itemPosterTitle.text = item.movie.title
        binding.itemPosterPost.loadFromUrl(
            Api.getPosterPath(item.movie.posterPath)
        )
    }
}

interface LikedMovieItemClickEvent {
    fun itemClick(item: LikedMovie)
}
