package id.mailstudio.detail

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.mailstudio.core.di.CoreModule
import id.mailstudio.core.di.RepositoryModule
import id.mailstudio.foodcatalogue.di.ModuleDependencies

@Component(
    dependencies = [ModuleDependencies::class],
    modules = [
        RepositoryModule::class
    ]
)
interface FoodDetailComponent {

    fun inject(activity: FoodDetailActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(searchModuleDependencies: ModuleDependencies): Builder
        fun build(): FoodDetailComponent
    }

}