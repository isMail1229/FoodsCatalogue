package id.mailstudio.search.ui.filter

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.EntryPointAccessors
import id.mailstudio.core.utils.Constants
import id.mailstudio.core.utils.ext.fragmentViewModel
import id.mailstudio.core.utils.ext.observe
import id.mailstudio.foodcatalogue.di.ModuleDependencies
import id.mailstudio.search.R
import id.mailstudio.search.databinding.FoodFilterFragmentBinding
import id.mailstudio.search.di.DaggerSearchComponent
import id.mailstudio.search.domain.FoodAreaUIModel
import id.mailstudio.search.domain.FoodAreaUIState
import id.mailstudio.search.domain.FoodCategoriesUIState
import id.mailstudio.search.domain.FoodCategoryUIModel
import id.mailstudio.search.domain.FoodFilterUIModel
import id.mailstudio.search.ui.FoodFilterSearchViewModel
import id.mailstudio.search.ui.ViewModelFactory
import javax.inject.Inject

class FoodFilterSearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FoodFilterFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FoodFilterSearchViewModel

    private val args by navArgs<FoodFilterSearchFragmentArgs>()

    private val categoryDropDownList = mutableListOf<FoodCategoryUIModel>()
    private val areaDropDownList = mutableListOf<FoodAreaUIModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSearchComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    ModuleDependencies::class.java
                )
            ).build()
            .inject(this)
        super.onCreate(savedInstanceState)
        viewModel = fragmentViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoodFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.abFilter.toolbar, navHostFragment)

        setRadioButton()
        observeViewModel()

        binding.btnApply.setOnClickListener {
            val dataCategory = viewModel.selectedCategory.value
            val dataArea = viewModel.selectedArea.value
            val dataFilterDomain = FoodFilterUIModel(
                if (dataCategory?.name.equals(getString(R.string.filter_all_label))) null else dataCategory,
                if (dataArea?.name.equals(getString(R.string.filter_all_label))) null else dataArea,
                getStatusFavorite()
            )
            setFragmentResult(
                Constants.FILTER_KEY,
                bundleOf(Constants.DATA_FILTER to dataFilterDomain)
            )
            findNavController().popBackStack()
        }

        binding.btnReset.setOnClickListener {
            viewModel.selectCategory(viewModel.getDefaultCategory(getString(R.string.filter_all_label)))
            viewModel.selectArea(viewModel.getDefaultArea(getString(R.string.filter_all_label)))
            viewModel.setFavorite(R.id.mb_all_favorite)
            observeViewModel()
        }
    }

    private fun setRadioButton() {
        binding.mbAllFavorite.text = getString(R.string.filter_all_label)
        binding.mbFavorite.text = getString(R.string.filter_favorite_label)
        binding.mbNotFavorite.text = getString(R.string.filter_un_favorite_label)

        if (!viewModel.isChange) {
            val argumentFilter = args.foodFilterUIModel
            viewModel.setFavorite(handleStatusFavorite(argumentFilter?.isFavorite))
            viewModel.selectCategory(
                argumentFilter?.category
                    ?: viewModel.getDefaultCategory(getString(R.string.filter_all_label))
            )
            viewModel.selectArea(
                argumentFilter?.area
                    ?: viewModel.getDefaultArea(getString(R.string.filter_all_label))
            )

            binding.radioGroupFavoriteStatus.clearCheck()
            binding.radioGroupFavoriteStatus.check(handleStatusFavorite(argumentFilter?.isFavorite))
            viewModel.isChange = true
        }

        binding.radioGroupFavoriteStatus.setOnCheckedChangeListener { _, optionId ->
            viewModel.setFavorite(optionId)
        }
    }

    private fun observeViewModel() {
        observe(viewModel.categories) {
            when (it) {
                is FoodCategoriesUIState.Loading -> {
                    // do something
                }
                is FoodCategoriesUIState.Error -> {
                    // do something
                }
                is FoodCategoriesUIState.Exists -> {
                    setupSpinnerCategories(it.list)
                }
            }
        }

        observe(viewModel.areas) {
            when (it) {
                is FoodAreaUIState.Loading -> {
                    // do something
                }
                is FoodAreaUIState.Error -> {
                    // do something
                }
                is FoodAreaUIState.Exists -> {
                    setupSpinnerAreas(it.list)
                }
            }
        }
    }

    private fun setupSpinnerCategories(categories: List<FoodCategoryUIModel>?) {
        categoryDropDownList.clear()
        categoryDropDownList.add(FoodCategoryUIModel(0, getString(R.string.filter_all_label)))
        if (categories != null) {
            categoryDropDownList.addAll(categories)
        }

        val adapter = object :
            ArrayAdapter<FoodCategoryUIModel>(
                requireContext(),
                R.layout.food_drop_down_item,
                categoryDropDownList.distinct()
            ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (view is TextView) view.text = categoryDropDownList.distinct()[position].name
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                if (view is TextView) {
                    view.text = categoryDropDownList.distinct()[position].name
                    view.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.black
                        )
                    )
                }
                return view
            }
        }

        binding.dropdownCategory.background = handleTheme()
        binding.dropdownCategory.adapter = adapter
        val categoryChooser = viewModel.selectedCategory.value ?: 0
        val position = categoryDropDownList.lastIndexOf(categoryChooser)
        binding.dropdownCategory.setSelection(position)
        setCategoriesAdapterListener {
            viewModel.selectCategory(it)
        }

    }

    private fun setCategoriesAdapterListener(callback: (FoodCategoryUIModel) -> Unit) {
        val adapterListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val dataSelected = categoryDropDownList.distinct()[position]
                if (dataSelected != viewModel.selectedCategory.value) {
                    callback.invoke(dataSelected)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        binding.dropdownCategory.onItemSelectedListener = adapterListener
    }

    private fun setupSpinnerAreas(foodArea: List<FoodAreaUIModel>?) {
        areaDropDownList.clear()
        areaDropDownList.add(FoodAreaUIModel(0, getString(R.string.filter_all_label)))
        if (foodArea != null) {
            areaDropDownList.addAll(foodArea)
        }
        val adapter = object :
            ArrayAdapter<FoodAreaUIModel>(
                requireContext(),
                R.layout.food_drop_down_item,
                areaDropDownList.distinct()
            ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (view is TextView) view.text = areaDropDownList.distinct()[position].name
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                if (view is TextView) {
                    view.text = areaDropDownList.distinct()[position].name
                    view.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.black
                        )
                    )
                }
                return view
            }
        }

        binding.dropdownArea.background = handleTheme()
        binding.dropdownArea.adapter = adapter
        val areaChooser = viewModel.selectedArea.value ?: 0
        val position = areaDropDownList.lastIndexOf(areaChooser)
        binding.dropdownArea.setSelection(position)
        setAreasAdapterListener {
            viewModel.selectArea(it)
        }
    }

    private fun setAreasAdapterListener(callback: (FoodAreaUIModel) -> Unit) {
        val adapterListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val dataSelected = areaDropDownList.distinct()[position]
                if (dataSelected != viewModel.selectedArea.value) {
                    callback.invoke(dataSelected)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        binding.dropdownArea.onItemSelectedListener = adapterListener
    }

    private fun handleTheme(): Drawable? {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_spinner_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_spinner)
            }
            else -> {
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_spinner)
            }
        }
    }

    private fun handleStatusFavorite(value: Boolean?): Int {
        return when (value) {
            true -> {
                R.id.mb_favorite
            }
            false -> {
                R.id.mb_not_favorite
            }
            else -> {
                R.id.mb_all_favorite
            }
        }
    }

    private fun getStatusFavorite(): Boolean? {
        return when (viewModel.statusFavorite.value) {
            R.id.mb_favorite -> true
            R.id.mb_not_favorite -> false
            R.id.mb_all_favorite -> null
            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}