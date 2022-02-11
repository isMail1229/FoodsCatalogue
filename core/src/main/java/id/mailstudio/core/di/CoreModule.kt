package id.mailstudio.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.mailstudio.core.utils.coroutine.CoroutineConfig
import id.mailstudio.core.utils.coroutine.CoroutineConfigImpl
import id.mailstudio.core.utils.preferences.FoodSharedPreference
import id.mailstudio.core.utils.preferences.FoodSharedPreferenceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun bindCoroutineDispatchers(coroutineConfigImpl: CoroutineConfigImpl): CoroutineConfig

    @Binds
    abstract fun bindFoodSharedPreferences(foodSharedPreferenceImpl: FoodSharedPreferenceImpl): FoodSharedPreference
}