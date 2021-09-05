package co.jonathanbernal.mercashop.data.remote.api

import co.jonathanbernal.mercashop.data.remote.service.MercaService
import co.jonathanbernal.mercashop.domain.models.SearchResponse
import io.reactivex.Observable
import javax.inject.Inject

class SearchApi @Inject constructor(
        private val mercaService: MercaService
) {

    /**
     * @author Jonathan Bernal
     * @param search se trata de la palabra o texto que el usuario digita en el searchview
     * @param offset pagina que se quiere descargar
     * @param limit limite de resultados por pagina
     * @return SearchResponse
     *      se encarga de conectar con el api para obtener el listado de productos a partir de la busqueda que realiza el usuario
     */
    fun searchProductsInApi(search: String, offset: Int, limit: Int): Observable<SearchResponse> {
        return mercaService.search(search, offset, limit)
    }
}