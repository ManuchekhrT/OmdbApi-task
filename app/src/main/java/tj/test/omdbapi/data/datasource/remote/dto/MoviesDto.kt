package tj.test.omdbapi.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("Search")
    val search: List<MovieDto> = listOf()
)

data class MovieDto(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String,
)