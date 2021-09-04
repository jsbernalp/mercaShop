package co.jonathanbernal.mercashop.domain.usecase

import co.jonathanbernal.mercashop.domain.Interfaces.IProductRepository
import co.jonathanbernal.mercashop.domain.models.Product
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class GetProductUseCaseTest {

    @Mock
    lateinit var iProductRepository: IProductRepository

    lateinit var getProductUseCase: GetProductUseCase

    @Before
    fun setUp() {
        getProductUseCase = GetProductUseCase(iProductRepository)
    }

    @Test
    fun `getProductByIdProduct muestra un loading al empezar`() {
        Mockito.`when`(iProductRepository.getProduct(any())).thenReturn(Observable.just(mock()))
        getProductUseCase.getProductByIdProduct("ML123456").test()
                .assertValueAt(0) { (it == GetProductUseCase.Result.Loading)}
    }

    @Test
    fun `getProductByIdProduct, devuelve el resultado de exito cuando se recupera correctamente el producto`(){
        val expectedProduct = Mockito.mock(Product::class.java)
        Mockito.`when`(iProductRepository.getProduct(any())).thenReturn(Observable.just(expectedProduct))
        getProductUseCase.getProductByIdProduct("ML123465").test()
                .assertValueAt(1){(it as GetProductUseCase.Result.Success).data == expectedProduct}

        verify(iProductRepository, times(1)).getProduct(any())
    }

    @Test
    fun `getProductByIdProduct, devuelve el resultado de la falla cuando se produce un error de timeout al recuperar el producto`(){
        val expectedError = TimeoutException()
        Mockito.`when`(iProductRepository.getProduct(any())).thenReturn(Observable.error(expectedError))

        getProductUseCase.getProductByIdProduct("ML123465").test()
                .assertValueAt(1){(it as GetProductUseCase.Result.Failure).throwable == expectedError}
    }

}