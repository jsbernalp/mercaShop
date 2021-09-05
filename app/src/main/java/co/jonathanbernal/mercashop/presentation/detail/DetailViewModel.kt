package co.jonathanbernal.mercashop.presentation.detail

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.common.utils.addTo
import co.jonathanbernal.mercashop.domain.models.Attribute
import co.jonathanbernal.mercashop.domain.models.Picture
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.usecase.GetProductUseCase
import co.jonathanbernal.mercashop.domain.usecase.GetProductUseCase.Result
import co.jonathanbernal.mercashop.presentation.detail.adapters.AttributeAdapter
import co.jonathanbernal.mercashop.presentation.detail.adapters.PictureAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
):ViewModel() {

    val progressVisible = ObservableBoolean()
    var pictures: MutableLiveData<List<Picture>> = MutableLiveData()
    var attributes: MutableLiveData<List<Attribute>> = MutableLiveData()
    var product = ObservableField<Product>()
    var pictureAdapter: PictureAdapter? = null
    var attributeAdapter: AttributeAdapter? = null
    val disposables = CompositeDisposable()

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

    /**
     * @author Jonathan Bernal
     *  Este metodo se encarga de realizar una peticion al caso de uso para obtener el objeto Result y lo envia al metodo handleGetProductResult para que el gestione la respuesta.
     */
    fun getProductDetail(id: String) {
        getProductUseCase.getProductByIdProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> handleGetProductResult(result) }
                .addTo(disposables)
    }

    /**
     * @author Jonathan Bernal
     * @param result contiene la respuesta de la peticion realizada en el metodo getProductDetail
     *      Este metodo se encarga de gestionar la respuesta, validando si es Correcta o Erronea, en el caso de ser correcta le asigna el valor a product y pictures que son un MutableLiveData de esta forma,
     *      se pintan los datos en la vista. En caso de ser erronea imprime un log con el error.
     */
    fun handleGetProductResult(result: Result){
        progressVisible.set(Result.Loading == result)
        when(result){
            is Result.Success -> {
                product.set(result.data)
                pictures.postValue(result.data.pictures)
                attributes.postValue(result.data.attributes)
            }
            is Result.Failure -> {
                Log.e(TAG,"error al intentar obtener producto ${result.throwable.message}")
            }
        }

    }


    /**
     * @author Jonathan Bernal
     * @param list listado de Imagenes
     *      se encarga de enviar el listado de Imagenes obtenidas de los detalles del producto al adapter para de esta forma añadirlos al recyclerview
     */
    fun setData(list: List<Picture>) {
        pictureAdapter?.setPictureList(list)
    }

    /**
     * @author Jonathan Bernal
     * @param position
     *      se encarga de retornar la imagen para pintarlo en el item utilizando databinding
     */
    fun getPicture(position: Int): Picture? {
        return pictures.value?.get(position)
    }

    /**
     * @author Jonathan Bernal
     *      se encarga de retornar el adapter que utilizara el recyclerview de las imagenes
     */
    fun getRecyclerPictureAdapter(): PictureAdapter?{
        pictureAdapter = PictureAdapter(this, R.layout.cell_picture)
        return pictureAdapter
    }


    /**
     * @author Jonathan Bernal
     * @param list listado de Caracteristicas
     *      se encarga de enviar el listado de caracteristicas obtenidas de los detalles del producto al adapter para de esta forma añadirlos al recyclerview
     */
    fun setDataAttributes(list: List<Attribute>) {
        attributeAdapter?.addAttributeList(list)
    }

    /**
     * @author Jonathan Bernal
     * @param position
     *      se encarga de retornar la imagen para pintarlo en el item utilizando databinding
     */
    fun getAttribute(position: Int): Attribute? {
        return attributes.value?.get(position)
    }


    /**
     * @author Jonathan Bernal
     *      se encarga de retornar el adapter que utilizara el recyclerview de las caracteristicas
     */
    fun getRecyclerAttributeAdapter(): AttributeAdapter?{
        attributeAdapter = AttributeAdapter(this, R.layout.cell_attribute)
        return attributeAdapter
    }


    /**
     * @author Jonathan Bernal
     *      se encarga de limpiar el disposable una vez se cierra la app.
     */
    fun unbound() {
        disposables.clear()
    }

}