package id.mailstudio.favorite

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.mailstudio.foodcatalogue.di.ModuleDependencies

@Component(
    dependencies = [ModuleDependencies::class]
)
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(searchModuleDependencies: ModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}

