package id.mailstudio.core.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.mailstudio.core.data.datasource.local.entity.FoodCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodCategoryDao {

    @Insert
    suspend fun insert(foodCategoryEntities: List<FoodCategoryEntity>)

    @Query("select * from category")
    fun getAllCategories(): Flow<List<FoodCategoryEntity>>
}