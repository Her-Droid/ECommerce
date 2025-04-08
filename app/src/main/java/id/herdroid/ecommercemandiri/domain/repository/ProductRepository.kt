package id.herdroid.ecommercemandiri.domain.repository

import id.herdroid.ecommercemandiri.domain.model.Cart
import id.herdroid.ecommercemandiri.domain.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getCategories(): List<String>
    suspend fun getProductsByCategory(category: String): List<Product>
    suspend fun getProductDetail(id: Int): Product


}