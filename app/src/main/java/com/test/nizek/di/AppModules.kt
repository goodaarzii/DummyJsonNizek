package com.test.nizek.di

import com.test.nizek.domin.repository.ProductRepository
import com.test.nizek.data.repositoryImpl.ProductRepositoryImpl
import com.test.nizek.data.services.DummyJsonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {


    @Provides
    @Singleton
    fun provideProductRepository(api: DummyJsonApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }
}
