package co.jonathanbernal.mercashop.presentation.recentsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import co.jonathanbernal.mercashop.R
import co.jonathanbernal.mercashop.databinding.FragmentSearchBinding
import co.jonathanbernal.mercashop.presentation.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class RecentSearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var recentSearchViewModel: RecentSearchViewModel

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

        recentSearchViewModel = activity.run {
            ViewModelProvider(this!!, viewModelFactory)[RecentSearchViewModel::class.java]
        }
        binding.searchList = recentSearchViewModel
        binding.recyclerViewSearchSuggestions.addItemDecoration(DividerItemDecoration(binding.recyclerViewSearchSuggestions.context,DividerItemDecoration.VERTICAL))

        recentSearchViewModel.getRecentSearchList()

        recentSearchViewModel.suggestions.observe(viewLifecycleOwner,{
            recentSearchViewModel.setData(it)
        })
    }




}