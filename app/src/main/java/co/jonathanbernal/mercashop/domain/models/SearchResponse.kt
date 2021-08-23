package co.jonathanbernal.mercashop.domain.models

data class SearchResponse(
        val site_id: String,
        val paging: Paging,
        val results: List<Product>
        )