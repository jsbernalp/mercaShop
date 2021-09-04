package co.jonathanbernal.mercashop.data.repository

import co.jonathanbernal.mercashop.data.database.RecentSearchDao
import co.jonathanbernal.mercashop.data.remote.api.SearchApi
import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.RecentSearch
import io.reactivex.Observable
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchApi: SearchApi,
    private val recentSearchDao: RecentSearchDao
) : ISearchyRepository {


    override fun searchProducts(
        search: String,
        offset: Int,
        limit: Int
    ): Observable<List<Product>> {
        return searchApi.searchProductsInApi(search, offset, limit)
            .map { it.results }
            .doOnNext { products ->
                if (products.isNotEmpty() && offset == 0){
                    val recentSearch = RecentSearch(search.toLowerCase())
                    insertRecentSearch(recentSearch)
                }
            }

    }

    override fun getRecentsSearches(): Observable<List<String>> {
        return recentSearchDao.getRecentsSearches()
                .map { recentsSearches ->
                    recentsSearches.map { recentSearch ->
                        recentSearch.text
                    } }
    }

    override fun insertRecentSearch(recentSearch: RecentSearch) {
        recentSearchDao.insertRecentSearch(recentSearch)
    }


}