package co.jonathanbernal.mercashop.domain.usecase

import co.jonathanbernal.mercashop.domain.Interfaces.IProductRepository
import co.jonathanbernal.mercashop.domain.models.Product
import io.reactivex.Observable
import javax.inject.Inject

class GetProductUseCase  @Inject constructor(
    private val iProductRepository: IProductRepository
) {

    sealed class Result {
        object Loading : Result()
        data class Success(val data: Product) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    /**
     * @author Jonathan Bernal
     * @param id este es el identificador del producto
     * @return Result
     *      este metodo se encarga de realizar una peticion al repositorio por medio de una interfaz para obtener la informacion de un producto a partir de su id
     *      una vez lo obtiene, lo mapea y lo convierte en un objeto de tipo Result o en el caso de que retorna un error lo convierte en un tipo Result y captura el error.
     */
    fun getProductByIdProduct(id: String): Observable<Result> {
        return iProductRepository.getProduct(id)
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }

}