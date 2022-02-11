package id.mailstudio.core.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.mailstudio.core.data.datasource.local.entity.FoodAreaEntity
import id.mailstudio.core.data.datasource.local.entity.FoodCategoryEntity
import id.mailstudio.core.data.datasource.local.entity.FoodEntity
import id.mailstudio.core.data.datasource.local.entity.FoodIngredient
import id.mailstudio.core.data.datasource.local.room.dao.FoodAreaDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodCategoryDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodIngredientDao

@Database(
    entities = [
        FoodEntity::class,
        FoodCategoryEntity::class,
        FoodAreaEntity::class,
        FoodIngredient::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    abstract fun foodCategoryDao(): FoodCategoryDao

    abstract fun foodAreaDao(): FoodAreaDao

    abstract fun foodIngredientDao(): FoodIngredientDao
}