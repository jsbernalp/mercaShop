package co.jonathanbernal.mercashop.presentation.results

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase
import co.jonathanbernal.mercashop.domain.usecase.SearchUseCase.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ResultViewModel @Inject constructor(
        private val searchUseCase: SearchUseCase
) : ViewModel() {

    var products: MutableLiveData<List<Product>> = MutableLiveData()
    var downloadedProducts = ArrayList<Product>()
    var searchText: MutableLiveData<String> = MutableLiveData()
    var productAdapter: ProductAdapter? = null
    var selectedProduct: MutableLiveData<String> = MutableLiveData()
    var textSearch: String? = null
    var offSet = 0
    var isDownloading = false

    companion object {
        private val TAG = ResultViewModel::class.java.simpleName
        private const val PAGE_SIZE = 30
    }

    fun getProductSearch() {
        if (!textSearch.isNullOrEmpty()) {
            if (!isDownloading) {
                isDownloading = true
                searchUseCase.search(textSearch!!, offSet, 30)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result -> handleSearchResponse(result) }
                        .isDisposed
            }
        }
    }

    fun handleSearchResponse(result: Result) {
        when (result) {
            is Result.Success -> {
                this.downloadedProducts.addAll(result.data as List<Product>)
                this.products.postValue(result.data)
            }
            is Result.Failure -> {
                Log.e(TAG, "error al intentar buscar un producto ${result.throwable.message}")
            }
        }
        isDownloading = false
    }

    fun openDetailProduct(position: Int) {
        selectedProduct.postValue(downloadedProducts[position].id)
    }

    fun setData(list: List<Product>) {
        productAdapter?.addProductList(list)
    }

    fun clearRecyclerView() {
        offSet = 0
        downloadedProducts.clear()
        productAdapter?.clearData()
    }

    fun getProduct(position: Int): Product {
        return downloadedProducts[position]
    }

    fun getProductPrice(position: Int): String {
        return "$ ${downloadedProducts[position].price}"
    }

    fun haveSeller(position: Int): Boolean {
        return !(downloadedProducts[position].seller?.eshop?.nick_name).isNullOrEmpty()
    }

    fun getRecyclerProductAdapter(): ProductAdapter? {
        productAdapter = ProductAdapter(this, R.layout.cell_product)
        return productAdapter
    }

    fun onLoadMoreData(visibleItemCount: Int, firstVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isInFooter(visibleItemCount, firstVisibleItemPosition, totalItemCount)) {
            return
        }
        offSet += PAGE_SIZE
        getProductSearch()
    }


    private fun isInFooter(
            visibleItemCount: Int,
            firstVisibleItemPosition: Int,
            totalItemCount: Int
    ): Boolean {
        return visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
    }

}