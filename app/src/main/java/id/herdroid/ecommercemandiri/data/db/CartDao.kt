package id.herdroid.ecommercemandiri.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getCart(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: CartEntity)

    @Update
    suspend fun update(cart: CartEntity)

    @Delete
    suspend fun delete(cart: CartEntity)

    @Query("DELETE FROM cart")
    suspend fun clearCart()
}