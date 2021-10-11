package co.zmt.themovie.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import co.zmt.themovie.R
import co.zmt.themovie.databinding.FragmentMovieDetailBinding
import co.zmt.themovie.helper.Api
import co.zmt.themovie.helper.loadFromUrl
import co.zmt.themovie.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    private val binding: FragmentMovieDetailBinding
        get() = _binding!!

    private val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieDetailLiveData(args.movieId).observe(viewLifecycleOwner) { data ->
            data?.movie?.apply {
                binding.movieDetailPoster.loadFromUrl(Api.getBackdropPath(backdropPath))
                binding.detailHeaderTitle.text = title
                binding.detailHeaderStar.rating = voteAverage.toFloat() / 2
                binding.detailBodySummary.text = overview
                binding.detailHeaderRelease.text = releaseDate
            }
            data?.favoriteMovie?.let {
                if (it.isFavorite) {
                    binding.fabFav.imageTintList =
                        ColorStateList.valueOf(requireContext().getColor(R.color.black))
                } else {
                    binding.fabFav.imageTintList =
                        ColorStateList.valueOf(requireContext().getColor(R.color.white))
                }
            }
            binding.fabFav.setOnClickListener {
                lifecycleScope.launch {
                    val favMovie = viewModel.getFavoriteMovie(data?.movie!!.movieId)
                    favMovie?.apply {
                        viewModel.insertFavoriteMovie(data,  !favMovie.isFavorite)
                    }
                    if (favMovie == null) {
                        viewModel.insertFavoriteMovie(data, true)
                    }
                }
            }
        }

    }

}