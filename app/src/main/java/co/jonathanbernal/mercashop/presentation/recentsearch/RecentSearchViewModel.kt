package co.jonathanbernal.mercashop.presentation.recentsearch


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.common.utils.addTo
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecentSearchViewModel @Inject constructor(
        private val searchUseCase: SearchUseCase
) : ViewModel() {

    var suggestions: MutableLiveData<List<String>> = MutableLiveData()
    var suggestionAdapter: SuggestionAdapter? = null
    var isDownloading = false
    var textSuggestion: MutableLiveData<String> = MutableLiveData()
    val disposables = CompositeDisposable()

    companion object {
        private val TAG = RecentSearchViewModel::class.java.simpleName
    }


    fun getRecentSearchList(){
        searchUseCase.getRecentsSearchs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {result->
              handleRecentSearchResponse(result)
            }.addTo(disposables)
    }

    fun handleRecentSearchResponse(result: Result){
        when(result){
            is Result.Success -> {
                suggestions.postValue(result.data as List<String>)
            }
            is Result.Failure -> {
                Log.e(TAG,"error al intentar obtener el listado de busquedas recientes ${result.throwable.message}")
            }
        }
        isDownloading = false
    }

    fun selectedSuggestion(position: Int){
        textSuggestion.postValue(suggestions.value?.get(position))
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

    fun unbound() {
        disposables.clear()
    }
}