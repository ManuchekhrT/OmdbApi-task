package tj.test.omdbapi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.test.omdbapi.domain.mapper.MoviesMapper
import tj.test.omdbapi.domain.repository.MoviesRepository
import tj.test.omdbapi.domain.usecases.MovieUseCase
import tj.test.omdbapi.domain.usecases.MoviesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideMoviesMapper() = MoviesMapper()

    @Singleton
    @Provides
    fun provideMediaUseCase(
        mapper: MoviesMapper,
        repository: MoviesRepository
    ): MovieUseCase = MoviesUseCaseImpl(mapper, repository)
}