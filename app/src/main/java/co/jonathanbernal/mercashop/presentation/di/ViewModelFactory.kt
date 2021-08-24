package co.jonathanbernal.mercashop.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.jonathanbernal.mercashop.presentation.search.SearchViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val searchViewModel: SearchViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(searchViewModel::class.java)) {
            return searchViewModel as T
        }
        throw IllegalArgumentException("uknown class name")
    }
}