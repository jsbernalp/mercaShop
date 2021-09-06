package co.jonathanbernal.mercashop.presentation



import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.presentation.detail.DetailViewModel
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import co.jonathanbernal.mercashop.presentation.results.ResultViewModel
import co.jonathanbernal.mercashop.presentation.recentsearch.RecentSearchViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var recentSearchViewModel: RecentSearchViewModel

    lateinit var resultViewModel: ResultViewModel

    lateinit var detailViewModel: DetailViewModel

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    var itemMenu : MenuItem? = null
    var searchView : SearchView? = null

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recentSearchViewModel = ViewModelProvider(this, viewModelFactory)[RecentSearchViewModel::class.java]
        resultViewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]
        detailViewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        recentSearchViewModel.textSuggestion.observe(this,{
            itemMenu!!.expandActionView()
            searchView!!.setQuery(it,true)
            searchView!!.clearFocus()
        })

        resultViewModel.selectedProduct.observe(this,{idProduct ->
            resultViewModel.unbound()
            changeDetailFragment(idProduct)
            searchView!!.clearFocus()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        itemMenu = menu?.findItem(R.id.search_action)
        searchView = itemMenu?.actionView as SearchView
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()){
                    resultViewModel.unbound()
                    changeResultFragment(query)
                }
                searchView!!.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    changeSearchFragment()
                }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun changeSearchFragment() {
        findNavController(this,R.id.nav_host).navigate(R.id.searchFragment)
    }

    private fun changeDetailFragment(idProduct: String) {
        itemMenu!!.collapseActionView()
        val bundle = bundleOf("idProduct" to idProduct)
        findNavController(this,R.id.nav_host).navigate(R.id.detailFragment,bundle)
    }

    private fun changeResultFragment(query: String) {
        findNavController(this,R.id.nav_host).navigate(R.id.resultFragment)
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            resultViewModel.searchText.postValue(query)
        }, 300)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector


    override fun onDestroy() {
        super.onDestroy()
        recentSearchViewModel.unbound()
        detailViewModel.unbound()
        resultViewModel.unbound()
    }


}