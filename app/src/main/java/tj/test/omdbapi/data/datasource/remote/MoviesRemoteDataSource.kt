package tj.test.omdbapi.data.datasource.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import tj.test.omdbapi.data.datasource.remote.dto.MoviesDto

class MoviesRemoteDataSource(
    private val api: MoviesApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchMovies(s: String, page: Int): MoviesDto = withContext(ioDispatcher) {
        api.fetchMovies(s, page)
    }
}