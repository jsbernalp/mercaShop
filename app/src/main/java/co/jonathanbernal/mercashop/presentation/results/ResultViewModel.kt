package co.jonathanbernal.mercashop.presentation.results

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.common.utils.addTo
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ResultViewModel @Inject constructor(
        private val searchUseCase: SearchUseCase
) : ViewModel() {

    val progressVisible = ObservableBoolean()
    val errorMessage : MutableLiveData<Int> = MutableLiveData()
    var products: MutableLiveData<List<Product>> = MutableLiveData()
    var downloadedProducts = ArrayList<Product>()
    var searchText: MutableLiveData<String> = MutableLiveData()
    var productAdapter: ProductAdapter? = null
    var selectedProduct: MutableLiveData<String> = MutableLiveData()
    var textSearch: String? = null
    var offSet = 0
    var isDownloading = false
    val disposables = CompositeDisposable()

    companion object {
        private val TAG = ResultViewModel::class.java.simpleName
        private const val PAGE_SIZE = 30
    }

    /**
     * @author Jonathan Bernal
     *  Este metodo se encarga de validar si el texto digitado por el usuario en el searchview es diferente de nulo o vacio, luego valida que no se este realizando una peticion (isDownloading),
     *  para evitar que se realicen peticiones de forma consecutiva sin que se completen, sobre todo en la paginacion, luego realiza una peticion al caso de uso para obtener el objeto Result y
     *  lo envia al metodo handleSearchResponse, para que el gestione la respuesta.
     */
    fun getProductSearch() {
        if (!textSearch.isNullOrEmpty()) {
            if (!isDownloading) {
                isDownloading = true
                searchUseCase.search(textSearch!!, offSet, 30)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result -> handleSearchResponse(result) }
                        .addTo(disposables)
            }
        }
    }

    /**
     * @author Jonathan Bernal
     * @param result contiene la respuesta de la peticion realizada en el metodo getProductSearch
     *      Este metodo se encarga de gestionar la respuesta, validando si es Correcta o Erronea, en el caso de ser correcta le asigna el valor a downloadedProducts y products, luego pone la variable
     *      is Downloading en false, para permitir que se haga una nueva peticion. En caso de ser erronea imprime un log con el error y envia un mensaje al usuario por medio de errorMessage para indicarle
     *      que ha ocurrido un error, luego pone isDownloading en false para que el usuario pueda reintentar.
     */
    fun handleSearchResponse(result: Result) {
        progressVisible.set(Result.Loading == result)
        when (result) {
            is Result.Success -> {
                downloadedProducts.addAll(result.data as List<Product>)
                products.postValue(result.data)
                isDownloading = false
            }
            is Result.Failure -> {
                Log.e(TAG, "error al intentar buscar un producto ${result.throwable.message}")
                errorMessage.postValue(R.string.errorInOperation)
                isDownloading = false
            }
        }

    }

    /**
     * @author Jonathan Bernal
     * @param position la posicion seleccionada en el recyclerview
     *  se encarga de enviar el id del producto seleccionado por medio del MutableLiveData para que al abrir el detalle, se pueda realizar la peticion al api para obtener la informacion.
     */
    fun openDetailProduct(position: Int) {
        selectedProduct.postValue(downloadedProducts[position].id)
    }

    /**
     * @author Jonathan Bernal
     * @param list listado de productos
     *      se encarga de enviar el listado de productos obtenidos del api al adapter para de esta forma añadirlos al recyclerview
     */
    fun setData(list: List<Product>) {
        productAdapter?.addProductList(list)
    }

    /**
     * @author Jonathan Bernal
     * Se encarga de limpiar los datos para realizar una nueva busqueda
     */
    fun clearRecyclerView() {
        offSet = 0
        downloadedProducts.clear()
        productAdapter?.clearData()
    }

    /**
     * @author Jonathan Bernal
     * @param position
     *      se encarga de retornar el producto para pintarlo en el item utilizando databinding
     */
    fun getProduct(position: Int): Product {
        return downloadedProducts[position]
    }

    /**
     * @author Jonathan Bernal
     * @param position
     *      se encarga de retornar el precio del producto para pintarlo en el item utilizando databinding
     */
    fun getProductPrice(position: Int): String {
        return "$ ${downloadedProducts[position].price}"
    }

    /**
     * @author Jonathan Bernal
     * @param position
     *      se encarga de retornar el valor boolean para pintar el nombre de la tienda en el item
     */
    fun haveSeller(position: Int): Boolean {
        return !(downloadedProducts[position].seller?.eshop?.nick_name).isNullOrEmpty()
    }


   /**
    * @author Jonathan Bernal
    *      se encarga de retornar el adapter que utilizara el recyclerview
    */
    fun getRecyclerProductAdapter(): ProductAdapter? {
        productAdapter = ProductAdapter(this, R.layout.cell_product)
        return productAdapter
    }

    /**
     * @author Jonathan Bernal
     * @param visibleItemCount
     * @param firstVisibleItemPosition
     * @param totalItemCount
     *      En primer lugar, el metodo estudia si la posicion en la que se encuentra el usuario en el recyclerview es la ultima para eso hace un llamado al metodo isInFooter, en caso de que sea
     *      falso, no realiza ninguna accion, en caso de ser verdadero, realiza una nueva peticion para obtener mas resultados de la busqueda aumenta en 30 el offset antes de realizar una nueva
     *      peticion.
     */
    fun onLoadMoreData(visibleItemCount: Int, firstVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isInFooter(visibleItemCount, firstVisibleItemPosition, totalItemCount)) {
            return
        }
        offSet += PAGE_SIZE
        getProductSearch()
    }


    /**
     * @author Jonathan Bernal
     * @param visibleItemCount
     * @param firstVisibleItemPosition
     * @param totalItemCount
     *      Estudia si el usuario se encuentra en la ultima posicion.
     */
    private fun isInFooter(
            visibleItemCount: Int,
            firstVisibleItemPosition: Int,
            totalItemCount: Int
    ): Boolean {
        return visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
    }


    /**
     * @author Jonathan Bernal
     *      se encarga de limpiar el disposable una vez se cierra la app.
     */
    fun unbound() {
        disposables.clear()
    }
}