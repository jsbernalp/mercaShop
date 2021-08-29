package co.jonathanbernal.mercashop.data.database

import androidx.room.Dao
import androidx.room.Query
import co.jonathanbernal.mercashop.domain.models.RecentSearch
import io.reactivex.Observable

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM RecentSearch")
    fun getRecentsSearches():Observable<List<RecentSearch>>

}