package co.jonathanbernal.mercashop.domain.Interfaces

import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable


interface ISearchyRepository {
    fun searchProducts(search: String, offset: Int, limit: Int): Observable<List<Product>>
}