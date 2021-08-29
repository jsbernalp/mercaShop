package co.jonathanbernal.mercashop.modules

import androidx.room.Room
import co.jonathanbernal.mercashop.data.database.MercaShopDatabase
import co.jonathanbernal.mercashop.data.database.RecentSearchDao
import co.jonathanbernal.mercashop.di.MercaShopApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMercaShopDatabase(application: MercaShopApplication): MercaShopDatabase = Room.databaseBuilder(application,
        MercaShopDatabase::class.java,"mercaShop_database")
        .build()

    @Provides
    @Singleton
    fun providesRecentSearchDao(database: MercaShopDatabase): RecentSearchDao = database.recentSearchDao()

}