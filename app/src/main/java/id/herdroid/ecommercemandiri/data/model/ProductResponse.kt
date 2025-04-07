package id.herdroid.ecommercemandiri.data.model

data class ProductResponse(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)