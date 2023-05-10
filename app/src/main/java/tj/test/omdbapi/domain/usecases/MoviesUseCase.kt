package tj.test.omdbapi.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.test.omdbapi.data.datasource.remote.dto.MovieDto
import tj.test.omdbapi.domain.mapper.MoviesMapper
import tj.test.omdbapi.domain.model.Movie
import tj.test.omdbapi.domain.model.State
import tj.test.omdbapi.domain.repository.MoviesRepository
import javax.inject.Inject

data class MoviesUseCaseParam(val search: String, val page: Int)

typealias MoviesList = List<Movie>

interface MovieUseCase : FlowUseCase<MoviesUseCaseParam, MoviesList>

class MoviesUseCaseImpl @Inject constructor(
    private val mapper: MoviesMapper,
    private val repository: MoviesRepository
) : MovieUseCase {

    override fun execute(param: MoviesUseCaseParam): Flow<State<MoviesList>> = flow {
        val allMovies = mutableListOf<MovieDto>()
        for (i in 1 until param.page + 1) {
            val moviesDto = repository.fetchMovies(param.search, param.page)
            allMovies.addAll(moviesDto)
        }
        emit(State.Success(allMovies.map(mapper::dtoToDomain)))
    }

}