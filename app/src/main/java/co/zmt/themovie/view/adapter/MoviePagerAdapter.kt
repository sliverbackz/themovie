package co.zmt.themovie.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.zmt.themovie.view.PopularMovieFragment
import co.zmt.themovie.view.UpcomingMovieFragment

class MoviePagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    companion object {
        private const val TAB_COUNT = 2
    }

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UpcomingMovieFragment.newInstance()
            1 -> PopularMovieFragment.newInstance()
            else -> throw Exception("fragment not found")
        }
    }
}