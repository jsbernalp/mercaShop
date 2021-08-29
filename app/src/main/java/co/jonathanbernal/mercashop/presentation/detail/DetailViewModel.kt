package co.jonathanbernal.mercashop.presentation.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.common.utils.addTo
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.usecase.GetProductUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
):ViewModel() {

    var product: MutableLiveData<Product> = MutableLiveData()
    val compositeDisposable = CompositeDisposable()

    fun getProductDetail(id: String){
        getProductUseCase.getProductByIdProduct(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
               product.postValue(it)
            }.addTo(compositeDisposable)
    }

}