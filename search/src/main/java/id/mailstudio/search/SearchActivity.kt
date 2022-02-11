package id.mailstudio.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.EntryPointAccessors
import id.mailstudio.foodcatalogue.di.ModuleDependencies
import id.mailstudio.search.di.DaggerSearchComponent

class SearchActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerSearchComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    ModuleDependencies::class.java
                )
            ).build()
            .inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_fragment_search) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder().build()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}