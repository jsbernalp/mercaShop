package co.jonathanbernal.mercashop.presentation.detail

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.common.utils.addTo
import co.jonathanbernal.mercashop.domain.models.Picture
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.usecase.GetProductUseCase
import co.jonathanbernal.mercashop.domain.usecase.GetProductUseCase.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
):ViewModel() {

    val progressVisible = ObservableBoolean()
    var pictures: MutableLiveData<List<Picture>> = MutableLiveData()
    var product = ObservableField<Product>()
    var pictureAdapter: PictureAdapter? = null
    val disposables = CompositeDisposable()

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

    fun getProductDetail(id: String) {
        getProductUseCase.getProductByIdProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> handleGetProductResult(result) }
                .addTo(disposables)
    }

    fun handleGetProductResult(result: Result){
        progressVisible.set(Result.Loading == result)
        when(result){
            is Result.Success -> {
                product.set(result.data)
                pictures.postValue(result.data.pictures)
            }
            is Result.Failure -> {
                Log.e(TAG,"error al intentar obtener producto ${result.throwable.message}")
            }
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

    fun unbound() {
        disposables.clear()
    }

}