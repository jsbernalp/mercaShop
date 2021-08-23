package co.jonathanbernal.mercashop.di

import co.jonathanbernal.mercashop.presentation.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {

    @ContributesAndroidInjector(modules = [])
    abstract fun bindMainActivity(): MainActivity

}