package id.mailstudio.search.ui.search_home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.EntryPointAccessors
import id.mailstudio.core.utils.Constants
import id.mailstudio.core.utils.TopBottomItemDecoration
import id.mailstudio.core.utils.ext.fragmentViewModel
import id.mailstudio.core.utils.ext.gone
import id.mailstudio.core.utils.ext.navigateSafe
import id.mailstudio.core.utils.ext.observe
import id.mailstudio.core.utils.ext.visible
import id.mailstudio.foodcatalogue.di.ModuleDependencies
import id.mailstudio.search.databinding.FoodSearchFragmentBinding
import id.mailstudio.search.di.DaggerSearchComponent
import id.mailstudio.search.domain.FoodFilterUIModel
import id.mailstudio.search.ui.FoodFilterSearchViewModel
import id.mailstudio.search.ui.ViewModelFactory
import id.mailstudio.search.ui.adapter.FoodSearchAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FoodSearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FoodSearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodFilterSearchViewModel by lazy {
        fragmentViewModel(viewModelFactory)
    }

    private val foodAdapter: FoodSearchAdapter by lazy {
        FoodSearchAdapter()
    }

    private val args by navArgs<FoodSearchFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerSearchComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    ModuleDependencies::class.java
                )
            ).build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoodSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.abSearch.toolbar, navHostFragment)

        if (viewModel.filterFood.value == null)
            args.filterFood?.let {
                viewModel.setFromParcelable(it)
            }

        with(binding.rvSearchList) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(
                TopBottomItemDecoration(
                    topSize = 16,
                    bottomSize = 16,
                    spaceHeight = 8
                )
            )
            adapter = foodAdapter
        }

        binding.etSearch.addTextChangedListener { editable ->
            editable?.let { query ->
                viewModel.searchOrFilterFood(query.toString(), viewModel.filterFood.value)
            }
        }

        binding.tvFilter.setOnClickListener {
            val action = FoodSearchFragmentDirections.actionToFoodFilterSearchFragment(
                viewModel.filterFood.value
            )
            findNavController().navigateSafe(action)
        }

        observeViewModel()

        setFragmentResultListener(Constants.FILTER_KEY) { _, bundle ->
            val dataFilter = bundle.getParcelable<FoodFilterUIModel>(Constants.DATA_FILTER)
            viewModel.searchOrFilterFood("", dataFilter)
            viewModel.setFromParcelable(dataFilter)
        }

        foodAdapter.onItemClick = { foodUIModel ->
            val uri = Uri.parse("foodcatalogue://detail")
            startActivity(Intent(Intent.ACTION_VIEW, uri).apply {
                putExtra(Constants.EXTRA_FOOD_DETAIL,foodUIModel)
            })
        }
    }

    private fun observeViewModel() {
        observe(viewModel.filterSearch) {
            if (it.isNotEmpty()) {
                foodAdapter.setItem(it)
                binding.rvSearchList.visible()
                binding.tvNotFound.gone()
            } else {
                binding.rvSearchList.gone()
                binding.tvNotFound.visible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}