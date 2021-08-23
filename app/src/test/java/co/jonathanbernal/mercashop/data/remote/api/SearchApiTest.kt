package co.jonathanbernal.mercashop.data.remote.api

import co.jonathanbernal.mercashop.data.remote.service.MercaService
import co.jonathanbernal.mercashop.domain.models.Paging
import co.jonathanbernal.mercashop.domain.models.Product
import co.jonathanbernal.mercashop.domain.models.SearchResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class SearchApiTest {

    @Mock
    lateinit var mercaService: MercaService

    lateinit var searchApi: SearchApi

    @Before
    fun setUp() {
        searchApi = SearchApi(mercaService)
    }


    @Test
    fun `searchProductsInApi, llama al metodo search de MercaService, una vez y retorna un objeto de tipo SearchResponse`(){
        val paging = Paging(123456,0,10)
        val product = Product("MLA717131873","Silla Eames Comedor Base Madera Varios Colores - Pack X 4",13765.5,500,250,"http://http2.mlstatic.com/D_804309-MLA46605828763_072021-O.jpg")
        val results = arrayListOf(product)
        val searchResponse = SearchResponse("MLA",paging,results)

        val search = "Silla"
        val offset = 0
        val limit = 10

        `when`(mercaService.search(any(), any(), any())).thenReturn(Observable.just(searchResponse))

        val resultTest = searchApi.searchProductsInApi(search,offset,limit).test()
        assertNotNull(resultTest)
        resultTest.assertNoErrors().assertValue(searchResponse)

        verify(mercaService, times(1)).search(any(), any(), any())
    }

    @Test
    fun `searchProductsInApi, llama al metodo search de MercaService, una vez y retorna un TimeoutException`(){
        val timeOutException = TimeoutException()

        val search = "Silla"
        val offset = 0
        val limit = 10

        `when`(mercaService.search(any(), any(), any())).thenReturn(Observable.error(timeOutException))

        val resultTest = searchApi.searchProductsInApi(search,offset,limit).test()
        resultTest.assertError(timeOutException)

        verify(mercaService, times(1)).search(any(), any(), any())
    }
}