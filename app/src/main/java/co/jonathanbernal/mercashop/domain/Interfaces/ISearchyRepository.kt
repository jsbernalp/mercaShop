package co.jonathanbernal.mercashop.domain.Interfaces

import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.RecentSearch
import io.reactivex.Observable


interface ISearchyRepository {

    fun searchProducts(search: String, offset: Int, limit: Int): Observable<List<Product>>
    fun getRecentsSearches():Observable<List<RecentSearch>>

    fun insertRecentSearch(recentSearch: RecentSearch)
}