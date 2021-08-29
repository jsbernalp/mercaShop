package co.jonathanbernal.mercashop.presentation.recentsearch


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecentSearchViewModel @Inject constructor(
        private val searchUseCase: SearchUseCase
) : ViewModel() {

    var suggestions: MutableLiveData<List<String>> = MutableLiveData()
    var searchText: MutableLiveData<String> = MutableLiveData()
    var suggestionAdapter: SuggestionAdapter? = null
    var isDownloading = false


    fun getRecentSearchList(){
        searchUseCase.getRecentsSearchs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { recentsSearches ->
                recentsSearches.map { recentSearch ->
                    recentSearch.text
                }
            }
            .subscribe {
                suggestions.postValue(it)
                isDownloading = false
            }.isDisposed
    }

    fun setData(list: List<String>) {
        suggestionAdapter?.setsuggestionList(list)
    }

    fun getSuggestion(position: Int): String?{
        val suggestions: MutableLiveData<List<String>> = suggestions
        return suggestions.value?.get(position)
    }

    fun getRecyclerSuggestionAdapter():SuggestionAdapter?{
        suggestionAdapter = SuggestionAdapter(this, R.layout.cell_suggestion)
        return suggestionAdapter
    }
}