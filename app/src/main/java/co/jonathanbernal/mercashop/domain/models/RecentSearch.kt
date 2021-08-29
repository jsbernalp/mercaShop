package co.jonathanbernal.mercashop.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "RecentSearch"
)
data class RecentSearch (
    @PrimaryKey
    @ColumnInfo(name = "text")
    val text: String
)