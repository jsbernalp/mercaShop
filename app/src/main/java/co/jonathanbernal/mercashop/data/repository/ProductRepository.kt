package co.jonathanbernal.mercashop.data.repository


import co.jonathanbernal.mercashop.data.remote.api.ProductApi
import co.jonathanbernal.mercashop.domain.Interfaces.IProductRepository
import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi
) : IProductRepository {

    /**
     * @author Jonathan Bernal
     * @param idProduct
     * @return Product
     *      Este metodo se encarga de realizar la peticion a la clase productApi, la cual retorna un List<ProductResponse> y
     *      este a su vez mapea esta respuesta y la convierte en un objeto Product, que es el que retorna a su observador
     */
    override fun getProduct(idProduct: String): Observable<Product> {
       return productApi.getProcessById(idProduct)
           .map { it[0].body }
    }
}