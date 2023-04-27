package com.aloarte.shopwise.di


import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasourceImpl
import com.aloarte.shopwise.data.repositories.ShopwiseProductsRepositoryImpl
import com.aloarte.shopwise.domain.ShopwiseProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindShopwiseProductsDatasource(impl: ShopwiseProductsDatasourceImpl): ShopwiseProductsDatasource
    @Binds
    abstract fun bindShopwiseProductsRepository(impl: ShopwiseProductsRepositoryImpl): ShopwiseProductsRepository
}