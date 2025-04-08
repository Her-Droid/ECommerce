package id.herdroid.ecommercemandiri.domain.model

data class Cart(
    val id: Int,
    val userId: Int,
    val date: String?,
    val products: List<CartProduct>
)