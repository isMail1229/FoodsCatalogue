package id.mailstudio.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.mailstudio.core.data.FoodRepository
import id.mailstudio.core.domain.repository.IFoodRepository

@Module(includes = [DatabaseModule::class, NetworkModule::class, CoreModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(foodRepository: FoodRepository): IFoodRepository
}