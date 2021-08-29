package co.jonathanbernal.mercashop.presentation



import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import co.jonathanbernal.mercashop.R
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

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recentSearchViewModel = ViewModelProvider(this, viewModelFactory)[RecentSearchViewModel::class.java]
        resultViewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()){
                    changeResultFragment(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    changeSearchFragment(newText)
                }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun changeSearchFragment(newText: String?) {
        Navigation.findNavController(this,R.id.nav_graph).navigate(R.id.searchFragment)
        recentSearchViewModel.searchText.postValue(newText)
    }

    private fun changeResultFragment(query: String) {
        Navigation.findNavController(this,R.id.nav_graph).navigate(R.id.resultFragment)
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            resultViewModel.searchText.postValue(query)
        }, 300)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector


}