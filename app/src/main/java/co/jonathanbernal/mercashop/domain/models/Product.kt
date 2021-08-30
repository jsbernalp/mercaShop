package co.jonathanbernal.mercashop.domain.models

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val original_price: Double?,
    val seller: Seller?,
    val available_quantity: Int,
    val sold_quantity: Int,
    val thumbnail: String,
    val pictures: List<Picture>
    )