package co.jonathanbernal.mercashop.di

import co.jonathanbernal.mercashop.presentation.MainActivity
import co.jonathanbernal.mercashop.presentation.di.FragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindMainActivity(): MainActivity

}