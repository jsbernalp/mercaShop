package co.jonathanbernal.mercashop.presentation.recentsearch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR

class SuggestionAdapter internal constructor(var recentSearchViewModel: RecentSearchViewModel, var resource: Int) :
    RecyclerView.Adapter<SuggestionAdapter.ViewHolder>() {

    private var suggestionListAdapter: List<String> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setSuggestionCard(recentSearchViewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    override fun getItemCount(): Int {
        Log.e("Prueba","tamaño del listado ${suggestionListAdapter.size}")
        return suggestionListAdapter.size
    }

    fun setsuggestionList(suggestions: List<String>) {
        this.suggestionListAdapter = suggestions.toList()
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: ViewDataBinding? = null

        init {
            this.binding = binding
        }

        fun setSuggestionCard(recentSearchViewModel: RecentSearchViewModel, position: Int) {
            binding?.setVariable(BR.itemSuggestion, recentSearchViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}