package tj.test.omdbapi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import tj.test.omdbapi.domain.model.State
import tj.test.omdbapi.domain.usecases.MovieUseCase
import tj.test.omdbapi.domain.usecases.MoviesList
import tj.test.omdbapi.domain.usecases.MoviesUseCaseParam
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val useCase: MovieUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<State<MoviesList>>()
    val movies: LiveData<State<MoviesList>> get() = _movies

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> get() = _page

    private val _search = MutableLiveData<String>()
    val search: LiveData<String> get() = _search

    fun setPage(page: Int) {
        _page.value = page
    }

    fun setSearch(search: String) {
        _search.value = search
    }

    fun fetchMovies() {
        viewModelScope.launch {
            useCase(MoviesUseCaseParam(search.value ?: "", page.value ?: 1))
                .onStart {
                    _movies.value = State.Loading
                }
                .catch {
                    _movies.value = State.Error(message = it.localizedMessage)
                }
                .collectLatest {
                    when(it) {
                        is State.Success -> _movies.value = State.Success(data = it.data)
                        is State.Error -> _movies.value = State.Error(it.error)
                        else -> _movies.value = State.Loading
                    }
                }
        }
    }
}