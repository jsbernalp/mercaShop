package co.jonathanbernal.mercashop.data.repository


import co.jonathanbernal.mercashop.data.remote.api.ProductApi
import co.jonathanbernal.mercashop.domain.Interfaces.IProductRepository
import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi
) : IProductRepository {

    override fun getProduct(idProduct: String): Observable<Product> {
       return productApi.getProcessById(idProduct)
           .map { it[0].body }
    }
}