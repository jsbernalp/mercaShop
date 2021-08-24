package co.jonathanbernal.mercashop.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: MercaShopApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideApplication(application: MercaShopApplication): Application = application

    @Provides
    @Singleton
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

}