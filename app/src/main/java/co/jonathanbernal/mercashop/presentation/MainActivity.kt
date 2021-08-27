package co.jonathanbernal.mercashop.presentation



import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import co.jonathanbernal.mercashop.presentation.search.SearchViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchViewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) searchViewModel.changeText(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        searchViewModel.changeText(newText)
                    }, 200)
                }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
    override fun androidInjector(): AndroidInjector<Any> = androidInjector


}