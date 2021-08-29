package co.jonathanbernal.mercashop.modules

import co.jonathanbernal.mercashop.data.database.RecentSearchDao
import co.jonathanbernal.mercashop.data.remote.api.ProductApi
import co.jonathanbernal.mercashop.data.remote.api.SearchApi
import co.jonathanbernal.mercashop.data.repository.ProductRepository
import co.jonathanbernal.mercashop.data.repository.SearchRepository
import co.jonathanbernal.mercashop.domain.Interfaces.IProductRepository
import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(searchApi: SearchApi, recentSearchDao: RecentSearchDao):ISearchyRepository{
        return SearchRepository(searchApi,recentSearchDao)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productApi: ProductApi):IProductRepository{
        return ProductRepository(productApi)
    }

}