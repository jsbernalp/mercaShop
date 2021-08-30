package co.jonathanbernal.mercashop.data.remote.api

import co.jonathanbernal.mercashop.data.remote.service.MercaService
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.ProductResponse
import io.reactivex.Observable
import javax.inject.Inject

class ProductApi @Inject constructor(
    private val mercaService: MercaService
) {

    fun getProcessById(idProduct: String): Observable<List<ProductResponse>> {
        return mercaService.getProduct(idProduct)
    }

}