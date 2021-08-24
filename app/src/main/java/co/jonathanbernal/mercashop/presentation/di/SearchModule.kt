package co.jonathanbernal.mercashop.presentation.di

import co.jonathanbernal.mercashop.presentation.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchModule {

    @ContributesAndroidInjector
    abstract fun providesSearchFragment():SearchFragment

}