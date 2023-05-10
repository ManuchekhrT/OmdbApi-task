package tj.test.omdbapi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import tj.test.omdbapi.data.datasource.remote.MoviesApi
import tj.test.omdbapi.data.datasource.remote.MoviesRemoteDataSource
import tj.test.omdbapi.domain.repository.MoviesRepository
import tj.test.omdbapi.data.repository.MoviesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(
        api: MoviesApi,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ) = MoviesRemoteDataSource(api, coroutineDispatcher)

    @Singleton
    @Provides
    fun provideMediaRepository(
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository = MoviesRepositoryImpl(moviesRemoteDataSource)

}