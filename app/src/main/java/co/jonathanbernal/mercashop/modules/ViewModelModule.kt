package co.jonathanbernal.mercashop.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.jonathanbernal.mercashop.di.ViewModelKey
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import co.jonathanbernal.mercashop.presentation.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun searchViewModel(viewModel: SearchViewModel): ViewModel
}