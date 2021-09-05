package co.jonathanbernal.mercashop.presentation.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import co.jonathanbernal.mercashop.domain.models.Attribute
import co.jonathanbernal.mercashop.presentation.detail.DetailViewModel

class AttributeAdapter internal constructor(var detailViewModel: DetailViewModel, var resource: Int) :
        RecyclerView.Adapter<AttributeAdapter.ViewHolder>() {

    private var attributeListAdapter = ArrayList<Attribute>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setAttributeCard(detailViewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    override fun getItemCount(): Int {
        return attributeListAdapter.size
    }

    fun addAttributeList(attributes: List<Attribute>) {
        this.attributeListAdapter.addAll(attributes)
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setAttributeCard(detailViewModel: DetailViewModel, position: Int) {
            binding?.setVariable(BR.itemAttribute, detailViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}