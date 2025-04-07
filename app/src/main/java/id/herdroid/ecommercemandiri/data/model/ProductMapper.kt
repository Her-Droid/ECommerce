package id.herdroid.ecommercemandiri.data.model

import id.herdroid.ecommercemandiri.domain.model.Product

fun ProductResponse.toDomain(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image
)
