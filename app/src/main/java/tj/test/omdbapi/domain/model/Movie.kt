package tj.test.omdbapi.domain.model

import java.io.Serializable

data class Movie(
    val imdbId: String,
    val title: String,
    val year: String,
    val type: String,
    val poster: String,
    var isLiked: Boolean = false,
    var commentsCount: Int = 0,
    var comments: List<String> = listOf()
): Serializable