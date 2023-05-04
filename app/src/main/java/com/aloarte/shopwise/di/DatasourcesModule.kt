package com.aloarte.shopwise.di


import com.aloarte.shopwise.data.datasources.CardsDatasource
import com.aloarte.shopwise.data.datasources.CardsDatasourceImpl
import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasource
import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasourceImpl
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasourceImpl
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
abstract class DataSourceModule {
    @Binds
    abstract fun bindShopwiseProductsDatasource(impl: ShopwiseProductsDatasourceImpl): ShopwiseProductsDatasource

    @Binds
    abstract fun bindProductsDescriptionDatasource(impl: ProductsDescriptionsDatasourceImpl): ProductsDescriptionsDatasource

    @Binds
    abstract fun bindCardsDatasource(impl: CardsDatasourceImpl): CardsDatasource

}