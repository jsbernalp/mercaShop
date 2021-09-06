package co.jonathanbernal.mercashop.domain.usecase

import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.RecentSearch
import co.jonathanbernal.mercashop.domain.models.SearchResponse
import io.reactivex.Observable
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val iSearchyRepository: ISearchyRepository
) {

    sealed class Result {
        object Loading : Result()
        data class Success(val data: List<Any>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    /**
     * @author Jonathan Bernal
     * @param search se trata de la palabra o texto que el usuario digita en el searchview
     * @param offset pagina que se quiere descargar
     * @param limit limite de resultados por pagina
     * @return Result
     *     este metodo se encarga de realizar una peticion al repositorio por medio de una interfaz para obtener el listado de productos a partir de una palabra o texto
     *     una vez lo obtiene, lo mapea y lo convierte en un objeto de tipo Result y captura el listado o en el caso de que retorna un error lo convierte en un tipo Result y captura el error.
     */
    fun search(search: String, offset: Int, limit: Int): Observable<Result> {
        return iSearchyRepository.searchProducts(search,offset,limit)
                .map { Result.Success(it) as Result  }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }

    /**
     *  @author Jonathan Bernal
     *      este metodo se encarga de realizar una peticion al repositorio por medio de una interfaz para obtener el listado de busquedas recientes almacenadas en la bd local,
     *      una vez lo obtiene, lo mapea y lo convierte en un objeto de tipo Result y captura el listado o en el caso de que retorna un error lo convierte en un tipo Result y captura el error.
     */
    fun getRecentsSearchs(): Observable<Result> {
        return iSearchyRepository.getRecentsSearches()
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
    }

}