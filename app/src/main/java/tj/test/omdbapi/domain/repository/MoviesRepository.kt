package tj.test.omdbapi.domain.repository

import tj.test.omdbapi.data.datasource.remote.dto.MovieDto

interface MoviesRepository {
    suspend fun fetchMovies(search: String, page: Int): List<MovieDto>
}