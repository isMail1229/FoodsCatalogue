package id.mailstudio.foodcatalogue.di

import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.core.utils.coroutine.CoroutineConfig
import id.mailstudio.core.utils.coroutine.CoroutineConfigImpl

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ModuleDependencies {

    fun foodUseCase(): FoodUseCase
}