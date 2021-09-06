package co.jonathanbernal.mercashop.data.remote.api

import co.jonathanbernal.mercashop.data.remote.service.MercaService
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.ProductResponse
import io.reactivex.Observable
import javax.inject.Inject

class ProductApi @Inject constructor(
    private val mercaService: MercaService
) {

    /**
     * @author Jonathan Bernal
     * @param idProduct
     * @return List<ProductResponse>
     *     Se encarga de realizar la peticion al api para obtener un producto a partir de su Id
     */
    fun getProcessById(idProduct: String): Observable<List<ProductResponse>> {
        return mercaService.getProduct(idProduct)
    }

}