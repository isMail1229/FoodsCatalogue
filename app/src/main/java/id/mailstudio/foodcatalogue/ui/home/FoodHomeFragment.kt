package id.mailstudio.foodcatalogue.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.mailstudio.core.utils.Constants
import id.mailstudio.core.utils.TopBottomItemDecoration
import id.mailstudio.core.utils.ext.gone
import id.mailstudio.core.utils.ext.navigateSafe
import id.mailstudio.core.utils.ext.observe
import id.mailstudio.core.utils.ext.visible
import id.mailstudio.foodcatalogue.R
import id.mailstudio.foodcatalogue.databinding.FoodHomeFragmentBinding
import id.mailstudio.foodcatalogue.domain.FoodModelUIState
import id.mailstudio.foodcatalogue.ui.FoodMainActivity
import id.mailstudio.foodcatalogue.ui.home.adapter.FoodListAdapter

@AndroidEntryPoint
class FoodHomeFragment : Fragment() {

    private var _binding: FoodHomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodHomeViewModel by viewModels()

    private val foodAdapter: FoodListAdapter by lazy {
        FoodListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoodHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.appbarMain.toolbar)
        setHasOptionsMenu(true)

        activity?.let {
            it.title = getString(R.string.app_name)
            binding.slFoodList.startShimmer()

            observeViewModel()

            with(binding.rvHomeListFood) {
                addItemDecoration(
                    TopBottomItemDecoration(
                        resources.getDimension(R.dimen._10sdp).toInt(),
                        resources.getDimension(R.dimen._16sdp).toInt(),
                        resources.getDimension(R.dimen._8sdp).toInt()
                    )
                )
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = foodAdapter
            }

            foodAdapter.onItemClick = { foodUIModel ->
                val uri = Uri.parse("foodcatalogue://detail")
                startActivity(Intent(Intent.ACTION_VIEW, uri).apply {
                    putExtra(Constants.EXTRA_FOOD_DETAIL, foodUIModel)
                })
            }
        }
    }

    private fun observeViewModel() {

        observe(viewModel.foods) {
            when (it) {
                is FoodModelUIState.Loading -> {
                    binding.gpErrorFoodList.gone()
                    binding.rvHomeListFood.gone()
                }
                is FoodModelUIState.Error -> {
                    binding.gpErrorFoodList.visible()
                    binding.slFoodList.gone()
                    binding.rvHomeListFood.gone()
                }
                is FoodModelUIState.Exists -> {
                    foodAdapter.setItem(it.list)
                    binding.rvHomeListFood.visible()
                    binding.gpErrorFoodList.gone()
                    binding.slFoodList.gone()
                }
            }
        }

        observe(viewModel.currentTheme) {
            it?.let { value ->
                when (value) {
                    0 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        (activity as FoodMainActivity).delegate.applyDayNight()
                    }
                    1 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        (activity as FoodMainActivity).delegate.applyDayNight()
                    }
                    2 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        (activity as FoodMainActivity).delegate.applyDayNight()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                val uri = Uri.parse("foodcatalogue://search")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
            R.id.menu_favorite -> {
                val uri = Uri.parse("foodcatalogue://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
            R.id.menu_setting -> {
                showSettingsDialog()
            }
            R.id.menu_info -> {
                val direction = FoodHomeFragmentDirections.actionToFoodInformationFragment()
                findNavController().navigateSafe(direction)
            }
            R.id.menu_about -> {
                val builder = AlertDialog.Builder(requireActivity()).apply {
                    setTitle(getString(R.string.about_label))
                    setMessage(
                        getString(R.string.about_message)
                    )
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose theme")
        val styles = arrayOf("Light", "Dark", "System default")
        val themeSelected = viewModel.currentTheme.value ?: 0

        builder.setSingleChoiceItems(styles, themeSelected) { dialog, which ->
            when (which) {
                0 -> {
                    viewModel.setTheme(0)
                    dialog.dismiss()
                }
                1 -> {
                    viewModel.setTheme(1)
                    dialog.dismiss()
                }
                2 -> {
                    viewModel.setTheme(2)
                    dialog.dismiss()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}