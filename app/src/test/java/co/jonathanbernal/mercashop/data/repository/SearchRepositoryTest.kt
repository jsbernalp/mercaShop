package co.jonathanbernal.mercashop.data.repository

import co.jonathanbernal.mercashop.data.database.RecentSearchDao
import co.jonathanbernal.mercashop.data.remote.api.SearchApi
import co.jonathanbernal.mercashop.domain.models.*
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeoutException


@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryTest {

    @Mock
    lateinit var searchApi: SearchApi

    @Mock
    lateinit var recentSearchDao: RecentSearchDao

    lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        searchRepository = SearchRepository(searchApi,recentSearchDao)
    }

    @Test
    fun `searchProductsInApi, llama al metodo searchProductsInApi, obtiene un objeto de SearchResponse, mapea el objeto, almacena la busqueda en bd y retorna un listado de productos `() {
        val paging = Paging(123456,0,10)
        val pictures = arrayListOf<Picture>(mock())
        val eshop = Eshop("asdasda")
        val seller = Seller(eshop)
        val product = Product("MLA717131873","Silla Eames Comedor Base Madera Varios Colores - Pack X 4",13765.5,123456.4,seller,500,250,"http://http2.mlstatic.com/D_804309-MLA46605828763_072021-O.jpg",pictures)
        val results = arrayListOf(product, product)
        val searchResponse = SearchResponse("MLA",paging,results)

        val search = "Silla"
        val offset = 0
        val limit = 10

        Mockito.`when`(searchApi.searchProductsInApi(any(), any(), any())).thenReturn(Observable.just(searchResponse))
        doNothing().`when`(recentSearchDao).insertRecentSearch(any())

        val resultTest = searchRepository.searchProducts(search, offset, limit).test()
        resultTest.assertNoErrors().assertValue(results)

        verify(searchApi, times(1)).searchProductsInApi(any(), any(), any())
        verify(recentSearchDao, times(1)).insertRecentSearch(any())
    }

    @Test
    fun `searchProductsInApi, llama al metodo searchProductsInApi, obtiene un error timeOut y lo retorna a su observador`() {
        val expectedError = TimeoutException()
        Mockito.`when`(searchApi.searchProductsInApi(any(), any(), any())).thenReturn(Observable.error(expectedError))

        val result = searchRepository.searchProducts("silla",0,30).test()
        result.assertError(expectedError)

        verify(searchApi, times(1)).searchProductsInApi(any(), any(), any())
    }



    @Test
    fun `getRecentsSearches, llama al metodo getRecentsSearches de la clase recentSearchDao, mapea la respuesta y retorna un listado de strings`() {
        val recentSearch = RecentSearch("monitor")
        val recentSearchList = arrayListOf(recentSearch,recentSearch,recentSearch)
        Mockito.`when`(recentSearchDao.getRecentsSearches()).thenReturn(Observable.just(recentSearchList))

        val result = searchRepository.getRecentsSearches().test()
        result.assertNoErrors().assertValue {recentsSearches->
            (recentsSearches == recentSearchList.map { it.text })
        }

        verify(recentSearchDao, times(1)).getRecentsSearches()
    }

    @Test
    fun `getRecentsSearches, llama al metodo getRecentsSearches de la clase RecentSearchDao, obtiene un error y lo retorna a su observador`() {
        val expectedError = Throwable()
        Mockito.`when`(recentSearchDao.getRecentsSearches()).thenReturn(Observable.error(expectedError))

        val result = searchRepository.getRecentsSearches().test()
        result.assertError(expectedError)

        verify(recentSearchDao, times(1)).getRecentsSearches()
    }



}

