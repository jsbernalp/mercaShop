package co.jonathanbernal.mercashop.data.remote.service

import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.ProductResponse
import co.jonathanbernal.mercashop.domain.models.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MercaService {

    @GET("sites/MLA/search")
    fun search(@Query("q") search: String,
               @Query("offset") offset: Int,
               @Query("limit") limit: Int
    ): Observable<SearchResponse>

    @GET("items")
    fun getProduct(@Query("ids") ids:String): Observable<List<ProductResponse>>

}