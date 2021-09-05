package co.jonathanbernal.mercashop.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.common.utils.isOnline
import co.jonathanbernal.mercashop.common.utils.toast
import co.jonathanbernal.mercashop.databinding.FragmentDetailBinding
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class DetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var detailViewModel: DetailViewModel

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)
        return binding.root
    }

    companion object {
        private val TAG = DetailFragment::class.java.simpleName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        val product = arguments?.getString("idProduct")
        Log.e("Prueba", "este es el producto que se recibe $product")

        detailViewModel = activity.run {
            ViewModelProvider(this!!, viewModelFactory)[DetailViewModel::class.java]
        }

        if (isOnline(requireContext())) {
            detailViewModel.getProductDetail(product!!)
        } else {
            requireContext().toast(R.string.isNotOnline)
        }

        binding.productDetail = detailViewModel

        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPicture.layoutManager = manager

        val managerAttribute = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewAttribute.layoutManager = managerAttribute

        detailViewModel.pictures.observe(viewLifecycleOwner, { pictures ->
            detailViewModel.setData(pictures)
        })

        detailViewModel.attributes.observe(viewLifecycleOwner,{attributes ->
            Log.e(TAG,"este es el listado de caracteristicas $attributes")
            detailViewModel.setDataAttributes(attributes)
        })

    }

}