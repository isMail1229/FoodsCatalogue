package id.mailstudio.search.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.mailstudio.foodcatalogue.di.ModuleDependencies
import id.mailstudio.search.SearchActivity
import id.mailstudio.search.ui.filter.FoodFilterSearchFragment
import id.mailstudio.search.ui.search_home.FoodSearchFragment

@Component(
    dependencies = [ModuleDependencies::class]
)
interface SearchComponent {

    fun inject(activity: SearchActivity)

    fun inject(fragmentSearchFragment: FoodSearchFragment)

    fun inject(foodFilterSearchFragment: FoodFilterSearchFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(searchModuleDependencies: ModuleDependencies): Builder
        fun build(): SearchComponent
    }
}