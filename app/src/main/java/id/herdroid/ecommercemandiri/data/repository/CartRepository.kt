package id.herdroid.ecommercemandiri.data.repository

import id.herdroid.ecommercemandiri.data.db.CartDao
import id.herdroid.ecommercemandiri.data.db.CartEntity
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    fun getCartItems() = cartDao.getCart()

    suspend fun addItem(item: CartEntity) = cartDao.insert(item)

    suspend fun updateItem(item: CartEntity) = cartDao.update(item)

    suspend fun removeItem(item: CartEntity) = cartDao.delete(item)

    suspend fun clearCart() = cartDao.clearCart()
}