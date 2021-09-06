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


    /**
     * @author Jonathan Bernal
     *  Este metodo se encarga de realizar una peticion al caso de uso para obtener el objeto Result y lo envia al metodo handleRecentSearchResponse para que el gestione la respuesta.
     */
    fun getRecentSearchList(){
        searchUseCase.getRecentsSearchs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {result->
              handleRecentSearchResponse(result)
            }.addTo(disposables)
    }


    /**
     * @author Jonathan Bernal
     * @param result contiene la respuesta de la peticion realizada en el metodo getRecentSearchList
     *      Este metodo se encarga de gestionar la respuesta, validando si es Correcta o Erronea, en el caso de ser correcta le asigna el valor a suggestions que es un MutableLiveData.
     *      En caso de ser erronea imprime un log con el error.
     */
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

    /**
     * @author Jonathan Bernal
     * @param position la posicion seleccionada en el recyclerview
     *  se encarga de enviar el texto de la busqueda reciente seleccionada por medio del MutableLiveData textSuggestion para que el searchview se autocomplete con este.
     */
    fun selectedSuggestion(position: Int){
        textSuggestion.postValue(suggestions.value?.get(position))
    }

    /**
     * @author Jonathan Bernal
     * @param list listado de Busquedas Recientes
     *      se encarga de enviar el listado de busquedas recientes obtenidas de la base de datos local al adapter para de esta forma añadirlos al recyclerview
     */
    fun setData(list: List<String>) {
        suggestionAdapter?.setsuggestionList(list)
    }


    /**
     * @author Jonathan Bernal
     * @param position
     *      se encarga de retornar el texto de la busqueda reciente para pintarlo en el item utilizando databinding
     */
    fun getSuggestion(position: Int): String?{
        val suggestions: MutableLiveData<List<String>> = suggestions
        return suggestions.value?.get(position)
    }


    /**
     * @author Jonathan Bernal
     *      se encarga de retornar el adapter que utilizara el recyclerview
     */
    fun getRecyclerSuggestionAdapter():SuggestionAdapter?{
        suggestionAdapter = SuggestionAdapter(this, R.layout.cell_suggestion)
        return suggestionAdapter
    }

    /**
     * @author Jonathan Bernal
     *      se encarga de limpiar el disposable una vez se cierra la app.
     */
    fun unbound() {
        disposables.clear()
    }
}