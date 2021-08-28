package co.jonathanbernal.mercashop.data.remote.api

import co.jonathanbernal.mercashop.data.remote.service.MercaService
import co.jonathanbernal.mercashop.domain.models.SearchResponse
import io.reactivex.Observable
import javax.inject.Inject

class SearchApi @Inject constructor(
        private val mercaService: MercaService
) {

    fun searchProductsInApi(search: String, offset: Int, limit: Int): Observable<SearchResponse> {
        return mercaService.search(search, offset, limit)
    }
}