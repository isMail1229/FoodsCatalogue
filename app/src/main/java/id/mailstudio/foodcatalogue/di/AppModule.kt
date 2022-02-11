package id.mailstudio.foodcatalogue.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.core.domain.usecase.FoodUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideFoodUseCase(foodUseCaseImpl: FoodUseCaseImpl): FoodUseCase
}