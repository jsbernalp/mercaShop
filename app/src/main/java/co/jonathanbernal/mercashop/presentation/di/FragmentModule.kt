package co.jonathanbernal.mercashop.presentation.di

import co.jonathanbernal.mercashop.presentation.results.ResultFragment
import co.jonathanbernal.mercashop.presentation.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun providesSearchFragment():SearchFragment

    @ContributesAndroidInjector
    abstract fun providesResultFragment():ResultFragment

}