package co.jonathanbernal.mercashop.domain.Interfaces

import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable

interface IProductRepository {

    fun getProduct(idProduct: String): Observable<Product>

}