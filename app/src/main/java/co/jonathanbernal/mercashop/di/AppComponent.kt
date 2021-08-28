package co.jonathanbernal.mercashop.di


import co.jonathanbernal.mercashop.modules.NetworkModule
import co.jonathanbernal.mercashop.modules.RepositoryModule
import co.jonathanbernal.mercashop.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
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