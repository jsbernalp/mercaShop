package co.jonathanbernal.mercashop.presentation.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.jonathanbernal.mercashop.domain.models.Picture
import androidx.databinding.library.baseAdapters.BR

class PictureAdapter internal constructor(var detailViewModel: DetailViewModel, var resource: Int) :
    RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    private var pictureListAdapter: List<Picture> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setPictureCard(detailViewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    override fun getItemCount(): Int {
        Log.e("Prueba","tamaño del listado ${pictureListAdapter.size}")
        return pictureListAdapter.size
    }

    fun setPictureList(pictures: List<Picture>) {
        this.pictureListAdapter = pictures.toList()
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setPictureCard(detailViewModel: DetailViewModel, position: Int) {
            binding?.setVariable(BR.itemPicture, detailViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}