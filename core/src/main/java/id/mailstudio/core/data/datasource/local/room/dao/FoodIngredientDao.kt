package id.mailstudio.core.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.mailstudio.core.data.datasource.local.entity.FoodIngredient

@Dao
interface FoodIngredientDao {

    @Insert
    suspend fun insert(foodIngredient: FoodIngredient)

    @Insert
    suspend fun insert(foodIngredients: List<FoodIngredient>)

    @Update
    suspend fun update(foodIngredient: FoodIngredient)

    @Query("select * from food_ingredient where food_id = :foodId")
    suspend fun getIngredientByFoodId(foodId: Long): List<FoodIngredient>?
}