package id.mailstudio.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.mailstudio.core.data.datasource.local.room.FoodDatabase
import id.mailstudio.core.data.datasource.local.room.dao.FoodAreaDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodCategoryDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodIngredientDao
import id.mailstudio.core.utils.Constants
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDefaultSharedPreferences(@ApplicationContext applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences(
            Constants.SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FoodDatabase =
        Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFoodDao(database: FoodDatabase): FoodDao =
        database.foodDao()

    @Provides
    fun provideFoodCategoryDao(database: FoodDatabase): FoodCategoryDao =
        database.foodCategoryDao()

    @Provides
    fun provideFoodAreaDao(database: FoodDatabase): FoodAreaDao =
        database.foodAreaDao()

    @Provides
    fun provideFoodIngredientDao(database: FoodDatabase): FoodIngredientDao =
        database.foodIngredientDao()
}