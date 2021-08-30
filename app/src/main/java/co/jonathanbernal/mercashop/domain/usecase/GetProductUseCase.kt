package co.jonathanbernal.mercashop.domain.usecase

import co.jonathanbernal.mercashop.domain.Interfaces.IProductRepository
import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable
import javax.inject.Inject

class GetProductUseCase  @Inject constructor(
    private val iProductRepository: IProductRepository
) {

    fun getProductByIdProduct(id: String):Observable<Product>{
        return iProductRepository.getProduct(id)
    }

}