package co.jonathanbernal.mercashop.data.remote.api

import co.jonathanbernal.mercashop.data.remote.service.MercaService
import co.jonathanbernal.mercashop.domain.models.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class ProductApiTest {

    @Mock
    lateinit var mercaService: MercaService

    lateinit var productApi: ProductApi

    @Before
    fun setUp() {
        productApi = ProductApi(mercaService)
    }

    @Test
    fun `searchProductsInApi, llama al metodo getProduct de MercaService, una vez y retorna un listado de tipo ProductResponse`(){
        val pictures = arrayListOf<Picture>(mock())
        val eshop = Eshop("asdasda")
        val seller = Seller(eshop)
        val product = Product("MLA717131873","Silla Eames Comedor Base Madera Varios Colores - Pack X 4",13765.5,12345.6,seller,500,250,"http://http2.mlstatic.com/D_804309-MLA46605828763_072021-O.jpg",pictures)
        val expectedResponse = ProductResponse(200,product)
        val listResponse = arrayListOf(expectedResponse)

        Mockito.`when`(mercaService.getProduct(any())).thenReturn(Observable.just(listResponse))

        val test = productApi.getProcessById("123456").test()
        test.assertNoErrors().assertValue(listResponse)

        verify(mercaService, times(1)).getProduct(any())
    }

    @Test
    fun `searchProductsInApi, llama al metodo getProduct de MercaService, una vez y retorna un error por timeout`(){
        val timeOutException = TimeoutException()

        Mockito.`when`(mercaService.getProduct(any())).thenReturn(Observable.error(timeOutException))

        val resultTest = productApi.getProcessById("ML123456").test()
        resultTest.assertError(timeOutException)

        verify(mercaService, times(1)).getProduct(any())
    }
}