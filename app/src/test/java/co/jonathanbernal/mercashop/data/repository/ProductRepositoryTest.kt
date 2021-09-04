package co.jonathanbernal.mercashop.data.repository

import co.jonathanbernal.mercashop.data.remote.api.ProductApi
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
class ProductRepositoryTest {

    @Mock
    lateinit var productApi: ProductApi

    lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        productRepository = ProductRepository(productApi)
    }

    @Test
    fun `getProduct, llama al metodo getProcessById de la clase ProductApi, obtiene un ListProductResponse, lo mapea y retorna un producto a su observador`() {
        val pictures = arrayListOf<Picture>(mock())
        val eshop = Eshop("asdasda")
        val seller = Seller(eshop)
        val product = Product("MLA717131873","Silla Eames Comedor Base Madera Varios Colores - Pack X 4",13765.5,12345.6,seller,500,250,"http://http2.mlstatic.com/D_804309-MLA46605828763_072021-O.jpg",pictures)
        val expectedResponse = ProductResponse(200,product)
        val listResponse = arrayListOf(expectedResponse,expectedResponse)

        Mockito.`when`(productApi.getProcessById(any())).thenReturn(Observable.just(listResponse))

        val result = productRepository.getProduct("ML123456").test()
        result.assertNoErrors().assertValue { product->
            (product == listResponse[0].body)
        }

        verify(productApi, times(1)).getProcessById(any())
    }

    @Test
    fun `getProduct, llama al metodo getProcessById de la clase ProductApi, obtiene una timeOutException y lo retorna un producto a su observador`() {
        val expectedError = TimeoutException()

        Mockito.`when`(productApi.getProcessById(any())).thenReturn(Observable.error(expectedError))

        val result = productRepository.getProduct("ML14532").test()
        result.assertError(expectedError)

        verify(productApi, times(1)).getProcessById(any())

    }
}