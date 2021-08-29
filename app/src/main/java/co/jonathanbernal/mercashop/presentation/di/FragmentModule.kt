package co.jonathanbernal.mercashop.presentation.di

import co.jonathanbernal.mercashop.presentation.detail.DetailFragment
import co.jonathanbernal.mercashop.presentation.results.ResultFragment
import co.jonathanbernal.mercashop.presentation.recentsearch.RecentSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun providesSearchFragment():RecentSearchFragment

    @ContributesAndroidInjector
    abstract fun providesResultFragment():ResultFragment

    @ContributesAndroidInjector
    abstract fun provideDetailFragmnet(): DetailFragment

}