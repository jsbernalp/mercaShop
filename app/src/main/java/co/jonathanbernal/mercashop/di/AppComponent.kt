package co.jonathanbernal.mercashop.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivitiesBuilder::class]
)
interface AppComponent : AndroidInjector<MercaShopApplication> {

    @Component.Builder
    interface Builder{

        fun build():AppComponent

        @BindsInstance
        fun application(application: MercaShopApplication):Builder
    }

}