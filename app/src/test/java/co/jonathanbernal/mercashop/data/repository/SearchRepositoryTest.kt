package co.jonathanbernal.mercashop.data.repository

import co.jonathanbernal.mercashop.data.remote.api.SearchApi
import co.jonathanbernal.mercashop.domain.models.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryTest {

    @Mock
    lateinit var searchApi: SearchApi

    lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        searchRepository = SearchRepository(searchApi)
    }

    @Test
    fun `searchProductsInApi, llama al metodo searchProductsInApi, obtiene un objeto de SearchResponse, mapea el objeto y retorna un listado de productos `() {
        val paging = Paging(123456,0,10)
        val eshop = Eshop("asdasda")
        val seller = Seller(eshop)
        val product = Product("MLA717131873","Silla Eames Comedor Base Madera Varios Colores - Pack X 4",13765.5,seller,500,250,"http://http2.mlstatic.com/D_804309-MLA46605828763_072021-O.jpg")
        val results = arrayListOf(product, product)
        val searchResponse = SearchResponse("MLA",paging,results)

        val search = "Silla"
        val offset = 0
        val limit = 10

        Mockito.`when`(searchApi.searchProductsInApi(any(), any(), any())).thenReturn(Observable.just(searchResponse))

        val resultTest = searchRepository.searchProducts(search, offset, limit).test()
        resultTest.assertNoErrors().assertValue(results)

        verify(searchApi, times(1)).searchProductsInApi(any(), any(), any())
    }

}

