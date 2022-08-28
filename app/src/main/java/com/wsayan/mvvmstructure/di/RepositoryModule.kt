package com.wsayan.mvvmstructure.di

import com.wsayan.mvvmstructure.repo.IMoviesRepository
import com.wsayan.mvvmstructure.repo.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideMoviesRepo(repository: MoviesRepository): IMoviesRepository
}