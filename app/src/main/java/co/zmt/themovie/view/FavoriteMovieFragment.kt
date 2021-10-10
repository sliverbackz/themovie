package co.zmt.themovie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.zmt.themovie.databinding.FragmentFavoriteMovieBinding
import co.zmt.themovie.model.local.db.movie.LikedMovie
import co.zmt.themovie.view.adapter.LikedMovieAdapter
import co.zmt.themovie.view.adapter.LikedMovieItemClickEvent
import co.zmt.themovie.viewmodel.FavoriteMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMovieFragment : Fragment() {
    private var _binding: FragmentFavoriteMovieBinding? = null

    private val binding: FragmentFavoriteMovieBinding
        get() = _binding!!

    private val viewModel: FavoriteMovieViewModel by viewModels()

    private val movieAdapter: LikedMovieAdapter by lazy {
        LikedMovieAdapter(object : LikedMovieItemClickEvent {
            override fun itemClick(item: LikedMovie) {
                val action =
                    MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(item.movie.id)
                findNavController().navigate(action)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovie.adapter = movieAdapter
        viewModel.favoriteMovieLiveData.observe(viewLifecycleOwner) {
            it?.apply { movieAdapter.submitList(this) }
        }
    }

}