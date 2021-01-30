package com.williamzabot.fullmoviedb.features.home.popular

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.williamzabot.fullmoviedb.R
import com.williamzabot.fullmoviedb.data.db.local.AppDatabase
import com.williamzabot.fullmoviedb.data.db.local.repository.PopularRepository
import com.williamzabot.fullmoviedb.data.remote.repository.MovieRepository
import com.williamzabot.fullmoviedb.extensions.internetIsOk

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    requireContext(),
                    MovieRepository(),
                    PopularRepository(
                        AppDatabase.getInstance(requireContext()).popularDAO
                    )
                ) as T
            }
        }
    }
    private val movieAdapter by lazy { MovieAdapter() }
    private val movieOffAdapter by lazy { MovieOffAdapter() }
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview_movies)
        recyclerView.adapter = movieAdapter
        inscribeObservers()
    }

    private fun inscribeObservers() {
        viewModel.pagedListMovie.observe(viewLifecycleOwner) { movies ->
            if (internetIsOk()) {
                movieAdapter.submitList(movies)
            }
        }

        viewModel.listMoviesOff.observe(viewLifecycleOwner) { movies ->
            recyclerView.adapter = movieOffAdapter
            movieOffAdapter.movies = movies
        }
    }

}