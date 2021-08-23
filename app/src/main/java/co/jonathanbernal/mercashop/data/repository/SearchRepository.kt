package co.jonathanbernal.mercashop.data.repository

import co.jonathanbernal.mercashop.data.remote.api.SearchApi
import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchApi: SearchApi
) : ISearchyRepository {


    override fun searchProducts(
        search: String,
        offset: Int,
        limit: Int
    ): Observable<List<Product>> {
        return searchApi.searchProductsInApi(search, offset, limit)
            .map { it.results }
    }


}