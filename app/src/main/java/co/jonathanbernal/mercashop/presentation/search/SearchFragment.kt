package co.jonathanbernal.mercashop.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.databinding.FragmentSearchBinding
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var searchViewModel: SearchViewModel

    lateinit var binding: FragmentSearchBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)

        searchViewModel = activity.run {
            ViewModelProviders.of(this!!, viewModelFactory).get(searchViewModel::class.java)
        }
        binding.searchList = searchViewModel
        binding.recyclerViewSearchSuggestions.addItemDecoration(DividerItemDecoration(binding.recyclerViewSearchSuggestions.context,DividerItemDecoration.VERTICAL))

        searchViewModel.searchText.observe(viewLifecycleOwner,{text->
            Log.e("SearchFragment","este es el texto $text")
            searchViewModel.getSuggestion(text)
        })

        searchViewModel.suggestions.observe(viewLifecycleOwner,{
            searchViewModel.setData(it)
        })
    }




}