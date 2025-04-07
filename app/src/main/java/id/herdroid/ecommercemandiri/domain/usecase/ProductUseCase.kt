package id.herdroid.ecommercemandiri.domain.usecase

import id.herdroid.ecommercemandiri.domain.model.Product
import id.herdroid.ecommercemandiri.domain.repository.ProductRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun getAllProducts(): List<Product> = repository.getAllProducts()
    suspend fun getCategories(): List<String> = repository.getCategories()

    suspend fun getProductsByCategory(category: String): List<Product> {
        val allProducts = repository.getAllProducts()
        return allProducts.filter { it.category.equals(category, ignoreCase = true) }
    }

    suspend fun getProductDetail(id: Int): Product = repository.getProductDetail(id)
}