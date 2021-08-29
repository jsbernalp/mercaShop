package co.jonathanbernal.mercashop.presentation.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.jonathanbernal.mercashop.domain.models.Product
import androidx.databinding.library.baseAdapters.BR

class ProductAdapter internal constructor(var resultViewModel: ResultViewModel, var resource: Int) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productListAdapter = ArrayList<Product>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setProductCard(resultViewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    override fun getItemCount(): Int {
        return productListAdapter.size
    }

    fun addProductList(products: List<Product>) {
        this.productListAdapter.addAll(products)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.productListAdapter.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setProductCard(resultViewModel: ResultViewModel, position: Int) {
            binding?.setVariable(BR.itemProduct, resultViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}