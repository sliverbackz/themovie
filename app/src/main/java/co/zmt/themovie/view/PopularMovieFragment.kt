package co.zmt.themovie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.zmt.themovie.R
import co.zmt.themovie.databinding.FragmentPopularMovieBinding
import co.zmt.themovie.helper.AsyncViewResource
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.view.adapter.MovieAdapter
import co.zmt.themovie.view.adapter.MovieItemClickEvent
import co.zmt.themovie.viewmodel.PopularMovieViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMovieFragment : Fragment() {
    private var _binding: FragmentPopularMovieBinding? = null

    private val binding: FragmentPopularMovieBinding
        get() = _binding!!

    private val viewModel: PopularMovieViewModel by viewModels()

    private lateinit var snackbar: Snackbar

    companion object {
        fun newInstance() = PopularMovieFragment()
    }

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(object : MovieItemClickEvent {
            override fun itemClick(item: MovieWithMovieGenre) {
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
        _binding = FragmentPopularMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularMovies()
        binding.rvMovie.adapter = movieAdapter
        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            it?.apply { movieAdapter.submitList(this) }
        }
        viewModel.movieStateLiveData.observe(viewLifecycleOwner) {
            snackbar = when (it) {
                is AsyncViewResource.Error -> {
                    Snackbar.make(binding.rvMovie, it.errorMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.lbl_load_again) {
                            viewModel.getPopularMovies()
                        }
                }
                is AsyncViewResource.Success -> {
                    Snackbar.make(binding.rvMovie, "Successful loaded", Snackbar.LENGTH_SHORT)

                }
                else -> {
                    Snackbar.make(binding.rvMovie, "Loading..", Snackbar.LENGTH_SHORT)
                }
            }
            snackbar.show()
        }

    }
}