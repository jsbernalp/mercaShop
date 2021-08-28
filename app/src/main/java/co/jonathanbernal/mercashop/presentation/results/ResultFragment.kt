package co.jonathanbernal.mercashop.presentation.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.databinding.FragmentResultBinding
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_result.*
import javax.inject.Inject


class ResultFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var resultViewModel: ResultViewModel

    lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result,container,false)
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


        resultViewModel.searchText.observe(viewLifecycleOwner,{query->
           resultViewModel.getProductSearch(query)
        })

        resultViewModel.products.observe(viewLifecycleOwner,{ products->
            resultViewModel.setData(products)
        })

    }
}