package co.zmt.themovie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.zmt.themovie.R.string
import co.zmt.themovie.databinding.FragmentUpcomingMovieBinding
import co.zmt.themovie.helper.AsyncViewResource.Error
import co.zmt.themovie.helper.AsyncViewResource.Success
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.view.adapter.MovieAdapter
import co.zmt.themovie.view.adapter.MovieItemClickEvent
import co.zmt.themovie.viewmodel.UpcomingMovieViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingMovieFragment : Fragment() {
    private lateinit var snackBar: Snackbar
    private var _binding: FragmentUpcomingMovieBinding? = null

    private val binding: FragmentUpcomingMovieBinding
        get() = _binding!!

    private val viewModel: UpcomingMovieViewModel by viewModels()

    companion object {
        fun newInstance() = UpcomingMovieFragment()
    }


    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(object : MovieItemClickEvent {
            override fun itemClick(item: MovieWithMovieGenre) {
                val action =
                    MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(item.movie.movieId)
                findNavController().navigate(action)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.getUpcomingMovies()
        viewModel.fetchUpcomingMovies()
        binding.rvMovie.adapter = movieAdapter
        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            it?.apply {
                binding.rvMovie.isVisible = true
                binding.emptyView.tvEmptyText.isVisible = false
                movieAdapter.submitList(this)
            }
        }
        viewModel.movieStateLiveData.observe(viewLifecycleOwner) {
            snackBar = when (it) {
                is Error -> {
                    val show = movieAdapter.itemCount > 0
                    binding.rvMovie.isVisible = show
                    binding.emptyView.tvEmptyText.isVisible = !show
                    binding.emptyView.tvEmptyText.text = "Empty Movie"
                    binding.loadingView.progressBar.isVisible = false
                    Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction(string.lbl_load_again) {
                            viewModel.fetchUpcomingMovies()
                        }
                }
                is Success -> {
                    binding.rvMovie.isVisible = true
                    binding.emptyView.tvEmptyText.isVisible = false
                    binding.loadingView.progressBar.isVisible = false
                    Snackbar.make(binding.root, "Successful loaded", Snackbar.LENGTH_SHORT)

                }
                else -> {
                    val show = movieAdapter.itemCount > 0
                    binding.rvMovie.isVisible = show
                    binding.emptyView.tvEmptyText.isVisible = !show
                    binding.emptyView.tvEmptyText.text = "Loading.."
                    binding.loadingView.progressBar.isVisible = !show
                    Snackbar.make(binding.root, "Loading..", Snackbar.LENGTH_SHORT)
                }
            }
            snackBar.show()
            if (movieAdapter.itemCount > 0) {
                snackBar.dismiss()
            }
        }
    }

}