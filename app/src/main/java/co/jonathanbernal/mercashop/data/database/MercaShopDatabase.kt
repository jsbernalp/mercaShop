package co.jonathanbernal.mercashop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.jonathanbernal.mercashop.domain.models.RecentSearch

@Database(entities = [RecentSearch::class],version = 1)
abstract class MercaShopDatabase: RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}