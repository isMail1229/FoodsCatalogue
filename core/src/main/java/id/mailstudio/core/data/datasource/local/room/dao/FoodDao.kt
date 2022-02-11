package id.mailstudio.core.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.mailstudio.core.data.datasource.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodDao {

    @Insert
    suspend fun insert(foods: List<FoodEntity>)

    @Update
    suspend fun update(food: FoodEntity)

    @Query("select * from food where food_id = :foodServerId")
    suspend fun getFoodByServerId(foodServerId: Long): FoodEntity?

    @Query("select * from food")
    fun getAllFoods(): Flow<List<FoodEntity>>

    @Query("select * FROM food where is_favorite = 1")
    fun getFavoriteFood(): Flow<List<FoodEntity>>

    @Query("select * from food where food_category like '%' || :categoryName || '%'")
    fun getFoodByCategoryName(categoryName: String): Flow<List<FoodEntity>>

    @Query("select * from food where food_area like '%' || :areaName || '%'")
    fun getFoodByAreaName(areaName: String): Flow<List<FoodEntity>>

    @Query("select * from food where food_name like '%' || :query || '%'")
    fun searchFood(query: String): Flow<List<FoodEntity>>

    @Query(
        "select * from food where food_category like '%' || :categoryName || '%' " +
                "and food_name like '%' || :query || '%'"
    )
    fun searchFoodWithCategoryName(query: String, categoryName: String): Flow<List<FoodEntity>>

    @Query(
        "select * from food where food_area like '%' || :areaName || '%' " +
                "and food_name like '%' || :query || '%'"
    )
    fun searchFoodWithAreaName(query: String, areaName: String): Flow<List<FoodEntity>>

    @Query(
        "select * from food where food_name like '%' || :query || '%' " +
                "and food_area like '%' || :areaName || '%' " +
                "and is_favorite = :isFavorite " +
                "and food_category like '%' || :categoryName || '%'"
    )
    fun searchFoodWithFilterValue(
        query: String,
        categoryName: String,
        areaName: String,
        isFavorite: Boolean
    ): Flow<List<FoodEntity>>

    @Query(
        "select * from food where food_name like '%' || :query || '%' " +
                "and food_area like '%' || :areaName || '%' " +
                "and food_category like '%' || :categoryName || '%'"
    )
    fun searchFoodJustCategoryAndArea(
        query: String,
        categoryName: String,
        areaName: String
    ): Flow<List<FoodEntity>>

    @Query(
        "select * from food where food_name like '%' || :query || '%' " +
                "and is_favorite = :isFavorite"
    )
    fun searchFoodWithFavorite(query: String, isFavorite: Boolean): Flow<List<FoodEntity>>

    @Query(
        "select * from food where food_category like '%' || :categoryName || '%'" +
                "and food_area like '%' || :areaName || '%' " +
                "and is_favorite = :isFavorite"
    )
    fun filterFoodWithFullValue(
        categoryName: String,
        areaName: String,
        isFavorite: Boolean
    ): Flow<List<FoodEntity>>

    @Query(
        "select * from food where food_category like '%' || :categoryName || '%' " +
                "and food_area like '%' || :areaName || '%' "
    )
    fun filterFoodJustCategoryAndArea(
        categoryName: String,
        areaName: String
    ): Flow<List<FoodEntity>>

    @Query("select * FROM food where is_favorite = :isFavorite")
    fun getFoodsWithFavorite(isFavorite: Boolean): Flow<List<FoodEntity>>
}