package tj.test.omdbapi.data.repository

import tj.test.omdbapi.data.datasource.remote.MoviesRemoteDataSource
import tj.test.omdbapi.data.datasource.remote.dto.MovieDto
import tj.test.omdbapi.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override suspend fun fetchMovies(search: String, page: Int): List<MovieDto> {
        return dataSource.fetchMovies(search, page).search
    }
}