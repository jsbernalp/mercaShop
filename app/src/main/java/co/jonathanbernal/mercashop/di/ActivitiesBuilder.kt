package co.jonathanbernal.mercashop.di

import co.jonathanbernal.mercashop.presentation.MainActivity
import co.jonathanbernal.mercashop.presentation.di.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {

    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun bindMainActivity(): MainActivity

}