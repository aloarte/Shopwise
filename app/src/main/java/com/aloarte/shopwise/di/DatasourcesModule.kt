package com.aloarte.shopwise.di


import com.aloarte.shopwise.data.datasources.CardsDatasource
import com.aloarte.shopwise.data.datasources.CardsDatasourceImpl
import com.aloarte.shopwise.data.datasources.LocalProductsDatasourceImpl
import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasource
import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasourceImpl
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindShopwiseProductsDatasource(impl: LocalProductsDatasourceImpl): ShopwiseProductsDatasource

    @Binds
    abstract fun bindProductsDescriptionDatasource(impl: ProductsDescriptionsDatasourceImpl): ProductsDescriptionsDatasource

    @Binds
    abstract fun bindCardsDatasource(impl: CardsDatasourceImpl): CardsDatasource

}