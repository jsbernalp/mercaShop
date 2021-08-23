package co.jonathanbernal.mercashop.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
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

}