package co.jonathanbernal.mercashop.presentation.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ResultViewModel@Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    var products: MutableLiveData<List<Product>> = MutableLiveData()
    var searchText: MutableLiveData<String> = MutableLiveData()
    var productAdapter: ProductAdapter? = null


    fun getProductSearch(query: String){
        searchUseCase.search(query,10,10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { products ->
                this.products.postValue(products)
            }
            .isDisposed
    }

    fun setData(list: List<Product>) {
        productAdapter?.setProductList(list)
    }

    fun getProduct(position: Int): Product?{
        val productList: MutableLiveData<List<Product>> = products
        return productList.value?.get(position)
    }

    fun getRecyclerProductAdapter(): ProductAdapter?{
        productAdapter = ProductAdapter(this, R.layout.cell_product)
        return productAdapter
    }

}