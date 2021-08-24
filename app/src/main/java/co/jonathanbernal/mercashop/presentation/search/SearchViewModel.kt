package co.jonathanbernal.mercashop.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(

) : ViewModel() {

    var searchText: MutableLiveData<String> = MutableLiveData()


    fun changeText(newText: String){
        searchText.postValue(newText)
    }
}