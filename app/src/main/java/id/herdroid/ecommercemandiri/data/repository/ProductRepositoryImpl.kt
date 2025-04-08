package id.herdroid.ecommercemandiri.data.repository

import id.herdroid.ecommercemandiri.data.api.ApiService
import javax.inject.Inject
import id.herdroid.ecommercemandiri.data.model.toDomain
import id.herdroid.ecommercemandiri.domain.model.Cart
import id.herdroid.ecommercemandiri.domain.model.Product
import id.herdroid.ecommercemandiri.domain.repository.ProductRepository


class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {
    override suspend fun getAllProducts(): List<Product> =
        api.getAllProducts().map { it.toDomain() }

    override suspend fun getCategories(): List<String> = api.getAllCategories()

    override suspend fun getProductsByCategory(category: String): List<Product> =
        api.getProductsByCategory(category).map { it.toDomain() }

    override suspend fun getProductDetail(id: Int): Product =
        api.getProductDetail(id).toDomain()


}