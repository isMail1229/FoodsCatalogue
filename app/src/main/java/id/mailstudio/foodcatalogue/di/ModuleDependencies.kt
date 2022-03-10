package id.mailstudio.foodcatalogue.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.mailstudio.core.domain.usecase.FoodUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ModuleDependencies {

    fun foodUseCase(): FoodUseCase
}