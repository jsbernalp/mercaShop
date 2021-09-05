package co.jonathanbernal.mercashop.data.repository

import co.jonathanbernal.mercashop.data.database.RecentSearchDao
import co.jonathanbernal.mercashop.data.remote.api.SearchApi
import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.RecentSearch
import io.reactivex.Observable
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchApi: SearchApi,
    private val recentSearchDao: RecentSearchDao
) : ISearchyRepository {


    /**
     * @author Jonathan Bernal
     * @param search se trata de la palabra o texto que el usuario digita en el searchview
     * @param offset pagina que se quiere descargar
     * @param limit limite de resultados por pagina
     * @return List<Product>
     *     Este metodo se encarga de realizar la peticion a la clase SearchApi, la cual retorna un objeto de tipo SearchResponse, una vez lo recibe lo mapea para obtener
     *     unicamente el listado de productos y luego valida si el listado es diferente de vacio y offset es = 0, es decir es la primera peticion de esta busqueda, guarda la palabra
     *     que ha digitado el usuario en el searchview en la bd.
     */
    override fun searchProducts(search: String, offset: Int, limit: Int): Observable<List<Product>> {
        return searchApi.searchProductsInApi(search, offset, limit)
            .map { it.results }
            .doOnNext { products ->
                if (products.isNotEmpty() && offset == 0){
                    val recentSearch = RecentSearch(search.toLowerCase())
                    insertRecentSearch(recentSearch)
                }
            }
    }

    /**
     * @author Jonathan Bernal
     * @return list<String>
     *     Este metodo se encarga de retornar el listado de busquedas recientes hechas por el usuario y que estan almacenadas en la bd local.
     */
    override fun getRecentsSearches(): Observable<List<String>> {
        return recentSearchDao.getRecentsSearches()
                .map { recentsSearches ->
                    recentsSearches.map { recentSearch ->
                        recentSearch.text
                    } }
    }

    /**
     * @author Jonathan Bernal
     * @param recentSearch objeto con la palabra ingresada por el usuario en el searchview
     *      Este metodo se encarga de inserta la busqueda reciente en la bd local, unicamente es llamado por el metodo "searchProducts" de esta clase
     */
    override fun insertRecentSearch(recentSearch: RecentSearch) {
        recentSearchDao.insertRecentSearch(recentSearch)
    }


}