package co.jonathanbernal.mercashop.domain.models

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val available_quantity: Int,
    val sold_quantity: Int,
    val thumbnail: String
        )