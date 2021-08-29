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

    fun search(search: String, offset: Int, limit: Int): Observable<List<Product>> {
        return iSearchyRepository.searchProducts(search,offset,limit)
    }

    fun getRecentsSearchs():Observable<List<RecentSearch>>{
        return iSearchyRepository.getRecentsSearches()
    }

}