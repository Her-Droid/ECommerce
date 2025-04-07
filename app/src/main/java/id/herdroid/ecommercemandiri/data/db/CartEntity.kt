package id.herdroid.ecommercemandiri.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    var quantity: Int = 1
)