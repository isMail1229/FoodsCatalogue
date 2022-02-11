package id.mailstudio.core.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.mailstudio.core.data.datasource.local.entity.FoodAreaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodAreaDao {

    @Insert
    suspend fun insert(foodAreas: List<FoodAreaEntity>)

    @Query("select * from area")
    fun getAllArea(): Flow<List<FoodAreaEntity>>
}