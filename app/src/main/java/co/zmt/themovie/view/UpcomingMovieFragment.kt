package co.zmt.themovie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.zmt.themovie.databinding.FragmentUpcomingMovieBinding
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.view.adapter.MovieAdapter
import co.zmt.themovie.view.adapter.MovieItemClickEvent
import co.zmt.themovie.viewmodel.UpcomingMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingMovieFragment : Fragment() {
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
                    MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(item.movie.id)
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
        viewModel.getUpcomingMovies()
        binding.rvMovie.adapter = movieAdapter
        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            it?.apply { movieAdapter.submitList(this) }
        }
    }

}