package co.jonathanbernal.mercashop.domain.usecase

import co.jonathanbernal.mercashop.domain.Interfaces.IProductRepository
import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable
import javax.inject.Inject

class GetProductUseCase  @Inject constructor(
    private val iProductRepository: IProductRepository
) {

    sealed class Result {
        data class Success(val data: Product) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun getProductByIdProduct(id: String): Observable<Result> {
        return iProductRepository.getProduct(id)
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
    }

}