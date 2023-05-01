package com.aloarte.shopwise.di


import com.aloarte.shopwise.data.repositories.CardsRepositoryImpl
import com.aloarte.shopwise.data.repositories.ShopwiseProductsRepositoryImpl
import com.aloarte.shopwise.domain.repositories.CardsRepository
import com.aloarte.shopwise.domain.repositories.ShopwiseProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindShopwiseProductsRepository(impl: ShopwiseProductsRepositoryImpl): ShopwiseProductsRepository

    @Binds
    abstract fun bindCardsRepository(impl: CardsRepositoryImpl): CardsRepository
}