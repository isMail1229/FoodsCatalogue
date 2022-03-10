package id.mailstudio.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.EntryPointAccessors
import id.mailstudio.core.utils.Constants
import id.mailstudio.core.utils.ext.gone
import id.mailstudio.core.utils.ext.visible
import id.mailstudio.favorite.databinding.ActivityFavoriteBinding
import id.mailstudio.foodcatalogue.R
import id.mailstudio.foodcatalogue.di.ModuleDependencies
import id.mailstudio.foodcatalogue.domain.toListFoodUIModel
import javax.inject.Inject

@Keep
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var factory: FavoriteFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    private val favoriteAdapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    ModuleDependencies::class.java
                )
            ).build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.title_favorite)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding.rvFavoriteListFoodFavorite) {
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        observeViewModel()

        favoriteAdapter.onItemClick = {
            val uri = Uri.parse("foodcatalogue://detail")
            startActivity(Intent(Intent.ACTION_VIEW, uri).apply {
                putExtra(Constants.EXTRA_FOOD_DETAIL, it)
            })
        }
    }

    private fun observeViewModel() {
        viewModel.favorite.observe(this) {
            favoriteAdapter.setItem(it.toListFoodUIModel())
            if (it.isNotEmpty()) {
                binding.rvFavoriteListFoodFavorite.visible()
                binding.tvNotFound.gone()
            } else {
                binding.rvFavoriteListFoodFavorite.gone()
                binding.tvNotFound.visible()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}