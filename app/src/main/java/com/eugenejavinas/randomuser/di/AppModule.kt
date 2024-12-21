package com.eugenejavinas.randomuser.di

import com.eugenejavinas.randomuser.common.rx.SchedulerProvider
import com.eugenejavinas.randomuser.common.rx.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindsSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider
}