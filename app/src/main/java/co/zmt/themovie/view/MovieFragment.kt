package co.zmt.themovie.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.zmt.themovie.R
import co.zmt.themovie.databinding.FragmentMovieBinding
import co.zmt.themovie.view.adapter.MoviePagerAdapter
import co.zmt.themovie.viewmodel.MovieViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null

    private val binding: FragmentMovieBinding
        get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_favorite -> {
                val action = MovieFragmentDirections.actionMovieFragmentToFavoriteMovieFragment()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPagerMovie.adapter = MoviePagerAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayoutMovie, binding.viewPagerMovie) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.lbl_upcoming_movie)
                }
                1 -> {
                    tab.text = getString(R.string.lbl_popular_movie)
                }
            }
        }.attach()
    }

}