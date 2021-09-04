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

    fun search(search: String, offset: Int, limit: Int): Observable<Result> {
        return iSearchyRepository.searchProducts(search,offset,limit)
                .map { Result.Success(it) as Result  }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }

    fun getRecentsSearchs(): Observable<Result> {
        return iSearchyRepository.getRecentsSearches()
                .map { Result.Success(it) as Result  }
                .onErrorReturn { Result.Failure(it) }
    }

}