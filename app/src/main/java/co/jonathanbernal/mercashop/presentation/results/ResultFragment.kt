package co.jonathanbernal.mercashop.presentation.results

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.databinding.FragmentResultBinding
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ResultFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var resultViewModel: ResultViewModel

    lateinit var binding: FragmentResultBinding

    private val onScrollListener: RecyclerView.OnScrollListener by lazy {
        object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                resultViewModel.onLoadMoreData(visibleItemCount, firstVisibleItemPosition, totalItemCount)
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        resultViewModel = activity.run {
            ViewModelProvider(this!!, viewModelFactory)[ResultViewModel::class.java]
        }

        binding.productList = resultViewModel
        binding.recyclerViewResultProduct.addItemDecoration(
                DividerItemDecoration(binding.recyclerViewResultProduct.context,
                        DividerItemDecoration.VERTICAL)
        )
        binding.recyclerViewResultProduct.run{
            addOnScrollListener(onScrollListener)
        }


        resultViewModel.searchText.observe(viewLifecycleOwner, { query ->
            resultViewModel.clearRecyclerView()
            resultViewModel.textSearch = query
            resultViewModel.offSet = 0
            resultViewModel.getProductSearch()
        })

        resultViewModel.products.observe(viewLifecycleOwner, { products ->
            resultViewModel.setData(products)
        })

    }

}