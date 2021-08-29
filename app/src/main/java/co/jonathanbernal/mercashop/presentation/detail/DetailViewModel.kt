package co.jonathanbernal.mercashop.presentation.detail

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.common.utils.addTo
import co.jonathanbernal.mercashop.domain.models.Picture
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.usecase.GetProductUseCase
import co.jonathanbernal.mercashop.presentation.recentsearch.SuggestionAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
):ViewModel() {

    var pictures: MutableLiveData<List<Picture>> = MutableLiveData()
    var product = ObservableField<Product>()
    var pictureAdapter: PictureAdapter? = null
    val compositeDisposable = CompositeDisposable()

    fun getProductDetail(id: String){
        getProductUseCase.getProductByIdProduct(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e("Prueba","esta es la informacion del producto $it")
               product.set(it)
                pictures.postValue(it.pictures)
            }.addTo(compositeDisposable)
    }

    fun getPrice():String{
        return if (product.get()?.price != null){
            product.get()?.price.toString()
        }else{
            ""
        }
    }

    fun setData(list: List<Picture>) {
        pictureAdapter?.setPictureList(list)
    }

    fun getPicture(position: Int): Picture? {
        return pictures.value?.get(position)
    }

    fun getRecyclerPictureAdapter(): PictureAdapter?{
        pictureAdapter = PictureAdapter(this, R.layout.cell_picture)
        return pictureAdapter
    }

}