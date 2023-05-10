package tj.test.omdbapi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tj.test.omdbapi.R
import tj.test.omdbapi.databinding.FragmentMoviesBinding
import tj.test.omdbapi.domain.model.Movie
import tj.test.omdbapi.domain.model.State
import tj.test.omdbapi.extensions.hide
import tj.test.omdbapi.extensions.show
import tj.test.omdbapi.extensions.showSnackBar
import java.net.UnknownHostException

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var movieAdapter: MovieAdapter? = null
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(SearchMovieDialog.REQUEST_KEY) { requestKey, bundle ->
            val search = bundle.getString(SearchMovieDialog.ARG_SEARCH, "")
            val quantity = bundle.getInt(SearchMovieDialog.ARG_QUANTITY, 1)
            viewModel.setSearch(search)
            viewModel.setPage(quantity)
            viewModel.fetchMovies()
        }
        initView()
        initObservers()
    }

    private fun initView() = with(binding) {
        movieAdapter = MovieAdapter {
            onMovieItemClickListener(it)
        }
        rvMovies.adapter = movieAdapter
        movieAdapter?.onLikeClickListener = { aMovie, _ ->
            aMovie.isLiked = !aMovie.isLiked
            movieAdapter?.updateItem(aMovie)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            if (viewModel.page.value != null && viewModel.search.value != null) {
                viewModel.fetchMovies()
            }
        }
        appToolbar.topAppBar.setOnClickListener {
            openSearchMovieDialog()
        }
    }

    private fun onMovieItemClickListener(item: Movie) {
        findNavController().navigate(
            R.id.action_MoviesFragment_to_MovieFragment,
            bundleOf(MovieFragment.ARG_MOVIE to item)
        )
    }

    private fun openSearchMovieDialog() {
        SearchMovieDialog().show(parentFragmentManager, SearchMovieDialog.TAG_SEARCH_MOVIE_DIALOG)
    }

    private fun initObservers() {
        viewModel.movies.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    binding.progressLoading.show()
                    binding.rvMovies.hide()
                    binding.textSetupSearchParam.hide()
                }
                is State.Success -> {
                    binding.progressLoading.hide()
                    binding.rvMovies.show()
                    binding.textSetupSearchParam.hide()

                    movieAdapter?.submitList(it.data)
                }
                is State.Error -> {
                    binding.progressLoading.hide()
                    binding.rvMovies.hide()
                    binding.textSetupSearchParam.show()

                    showError(it.error, it.message)
                }
            }
        }
    }

    private fun showError(error: Throwable?, message: String?) {
        val errorMessage = StringBuilder("")
        if (error is UnknownHostException) {
            errorMessage.append(resources.getString(R.string.error_network_connection))
        } else {
            errorMessage.append(message)
        }

        binding.root.showSnackBar(errorMessage.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}