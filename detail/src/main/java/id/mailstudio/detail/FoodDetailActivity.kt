package id.mailstudio.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.EntryPointAccessors
import id.mailstudio.core.utils.Constants
import id.mailstudio.detail.databinding.ActivityFoodDetailBinding
import id.mailstudio.foodcatalogue.R
import id.mailstudio.foodcatalogue.di.ModuleDependencies
import id.mailstudio.foodcatalogue.domain.FoodUIModel
import javax.inject.Inject

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding

    @Inject
    lateinit var factory: FoodDetailViewModelFactory

    private val ingredientAdapter: DetailIngredientAdapter by lazy {
        DetailIngredientAdapter()
    }

    private val viewModel: FoodDetailViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFoodDetailComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    ModuleDependencies::class.java
                )
            ).build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.title_food_detail)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding.rvDetailIngredient) {
            layoutManager =
                LinearLayoutManager(this@FoodDetailActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = ingredientAdapter
        }

        val dataFood = intent.getParcelableExtra<FoodUIModel>(Constants.EXTRA_FOOD_DETAIL)
        viewModel.setFoodFromParcelable(dataFood)

        binding.fabDetailFavorite.setOnClickListener {
            val isFavorite = viewModel.isFavorite.value ?: false

            viewModel.setFavoriteFood(!isFavorite, viewModel.foodModel.value)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.foodModel.observe(this, { foodModel ->
            foodModel?.let {
                Glide.with(this)
                    .load(it.foodImage)
                    .into(binding.ivDetailFoodThumbnail)

                binding.tvDetailFoodName.text = it.foodName
                binding.tvDetailCategory.text = it.foodCategory
                binding.tvDetailArea.text = it.foodArea
                binding.tvInstruction.text = it.foodInstruction
                setStatusFavorite(it.isFavorite)
            }
        })

        viewModel.isFavorite.observe(this, {
            setStatusFavorite(it)
        })

        viewModel.foodIngredient.observe(this, {
            if (it != null)
                ingredientAdapter.setItem(it)
        })
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabDetailFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_fill
                )
            )
        } else {
            binding.fabDetailFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_non_fill
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}