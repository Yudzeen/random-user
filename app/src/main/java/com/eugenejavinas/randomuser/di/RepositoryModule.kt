package com.eugenejavinas.randomuser.di

import com.eugenejavinas.randomuser.data.network.RandomUserApi
import com.eugenejavinas.randomuser.data.repository.UserRepository
import com.eugenejavinas.randomuser.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(randomUserApi: RandomUserApi): UserRepository =
        UserRepositoryImpl(randomUserApi)

    @Singleton
    @Provides
    fun provideRandomUserApi(): RandomUserApi = RandomUserApi.create()
}