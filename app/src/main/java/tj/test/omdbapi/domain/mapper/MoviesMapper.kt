package tj.test.omdbapi.domain.mapper

import tj.test.omdbapi.data.datasource.remote.dto.MovieDto
import tj.test.omdbapi.domain.model.Movie

class MoviesMapper : Mapper<Movie, MovieDto>() {

    override fun dtoToDomain(dto: MovieDto) = with(dto) {
        Movie(
            title = title,
            year = year,
            imdbId = imdbID,
            type = type,
            poster = poster
        )
    }

    override fun domainToDto(domain: Movie): MovieDto {
        TODO("Not yet implemented")
    }
}