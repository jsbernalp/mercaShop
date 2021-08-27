package co.jonathanbernal.mercashop.presentation.results

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.jonathanbernal.mercashop.domain.models.Product
import androidx.databinding.library.baseAdapters.BR

class ProductAdapter internal constructor(var resultViewModel: ResultViewModel, var resource: Int) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productListAdapter: List<Product> = mutableListOf()


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
        Log.e("Prueba","position $position")
        return resource
    }

    override fun getItemCount(): Int {
        Log.e("Prueba","tamaño del listado ${productListAdapter.size}")
        return productListAdapter.size
    }

    fun setProductList(products: List<Product>) {
        this.productListAdapter = products.toList()
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