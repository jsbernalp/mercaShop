package co.jonathanbernal.mercashop.domain.usecase


import co.jonathanbernal.mercashop.domain.Interfaces.ISearchyRepository
import co.jonathanbernal.mercashop.domain.models.Product
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
class SearchUseCaseTest {

    @Mock
    lateinit var iSearchyRepository: ISearchyRepository

    lateinit var searchUseCase: SearchUseCase

    @Before
    fun setUp() {
        searchUseCase = SearchUseCase(iSearchyRepository)
    }

    @Test
    fun `search muestra un loading al empezar`() {
        Mockito.`when`(iSearchyRepository.searchProducts(any(), any(), any())).thenReturn(Observable.just(mock()))
        searchUseCase.search("silla",0,30).test()
                .assertValueAt(0) { (it == SearchUseCase.Result.Loading)}
    }

    @Test
    fun `search, devuelve el resultado de exito cuando se recupera correctamente el resultado de la busqueda`(){
        val product = Mockito.mock(Product::class.java)
        val expectedProductList = arrayListOf(product,product)
        Mockito.`when`(iSearchyRepository.searchProducts(any(), any(), any())).thenReturn(Observable.just(expectedProductList))
        searchUseCase.search("silla",0,30).test()
                .assertValueAt(1){(it as SearchUseCase.Result.Success).data == expectedProductList}

        verify(iSearchyRepository, times(1)).searchProducts(any(), any(), any())
    }

    @Test
    fun `search, devuelve el resultado de la falla cuando se produce un error de timeout al recuperar el producto`(){
        val expectedError = TimeoutException()
        Mockito.`when`(iSearchyRepository.searchProducts(any(), any(), any())).thenReturn(Observable.error(expectedError))

        searchUseCase.search("ML123465",0,30).test()
                .assertValueAt(1){(it as SearchUseCase.Result.Failure).throwable == expectedError}
    }

    @Test
    fun `getRecentsSearchs, devuelve el resultado de exito cuando se recupera correctamente el listado de busquedas recientes`(){
        val expectedRecentSearchList = arrayListOf("Silla","Mesa","Mochila")
        Mockito.`when`(iSearchyRepository.getRecentsSearches()).thenReturn(Observable.just(expectedRecentSearchList))
        searchUseCase.getRecentsSearchs().test()
                .assertNoErrors()
                .assertValueAt(0){(it as SearchUseCase.Result.Success).data == expectedRecentSearchList}

        verify(iSearchyRepository, times(1)).getRecentsSearches()

    }

    @Test
    fun `getRecentsSearchs, devuelve el resultado de la falla cuando se produce un error al recuperar el listado de busquedas recientes`(){
        val expectedError = Throwable()
        Mockito.`when`(iSearchyRepository.getRecentsSearches()).thenReturn(Observable.error(expectedError))

        searchUseCase.getRecentsSearchs().test()
                .assertValueAt(0){(it as SearchUseCase.Result.Failure).throwable == expectedError}
    }
}