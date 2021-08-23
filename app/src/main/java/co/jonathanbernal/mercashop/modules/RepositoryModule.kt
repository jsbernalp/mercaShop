package co.jonathanbernal.mercashop.modules

import co.jonathanbernal.mercashop.data.remote.api.SearchApi
import co.jonathanbernal.mercashop.data.repository.SearchRepository
import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(searchApi: SearchApi):ISearchyRepository{
        return SearchRepository(searchApi)
    }
}