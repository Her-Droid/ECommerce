package id.herdroid.ecommercemandiri.data.api

import id.herdroid.ecommercemandiri.data.model.ProductResponse
import id.herdroid.ecommercemandiri.domain.model.Cart
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<ProductResponse>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): ProductResponse

}