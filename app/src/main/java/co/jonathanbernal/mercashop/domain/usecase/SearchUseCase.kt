package co.jonathanbernal.mercashop.domain.usecase

import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.SearchResponse
import io.reactivex.Observable
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val iSearchyRepository: ISearchyRepository
) {

    fun search(search: String, offset: Int, limit: Int): Observable<List<String>> {
        return iSearchyRepository.searchProducts(search,offset,limit)
                .map {products ->
                    products.map { product ->
                        product.title
                    }
                }
    }

}