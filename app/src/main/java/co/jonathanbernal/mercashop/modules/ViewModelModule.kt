package co.jonathanbernal.mercashop.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.jonathanbernal.mercashop.di.ViewModelKey
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import co.jonathanbernal.mercashop.presentation.results.ResultViewModel
import co.jonathanbernal.mercashop.presentation.recentsearch.RecentSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecentSearchViewModel::class)
    abstract fun searchViewModel(viewModelRecent: RecentSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    abstract fun resultViewModel(viewModel: ResultViewModel):ViewModel

}