package tj.test.omdbapi.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query
import tj.test.omdbapi.data.datasource.remote.dto.MoviesDto
import tj.test.omdbapi.extensions.OMDB_API_KEY

interface MoviesApi {
    @GET("?apikey=$OMDB_API_KEY")
    suspend fun fetchMovies(
        @Query("s") search: String,
        @Query("page") page: Int
    ): MoviesDto
}