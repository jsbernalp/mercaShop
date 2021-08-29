package co.jonathanbernal.mercashop.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(

):ViewModel() {

    var backFragment: MutableLiveData<String> = MutableLiveData()

}