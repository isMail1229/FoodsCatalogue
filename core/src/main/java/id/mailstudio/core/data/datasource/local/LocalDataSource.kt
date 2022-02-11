package id.mailstudio.core.data.datasource.local

import id.mailstudio.core.data.datasource.local.entity.FoodAreaEntity
import id.mailstudio.core.data.datasource.local.entity.FoodCategoryEntity
import id.mailstudio.core.data.datasource.local.entity.FoodEntity
import id.mailstudio.core.data.datasource.local.entity.FoodIngredient
import id.mailstudio.core.data.datasource.local.room.dao.FoodAreaDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodCategoryDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodDao
import id.mailstudio.core.data.datasource.local.room.dao.FoodIngredientDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalDataSource @Inject constructor(
    private val foodDao: FoodDao,
    private val foodCategoryDao: FoodCategoryDao,
    private val foodAreaDao: FoodAreaDao,
    private val foodIngredientDao: FoodIngredientDao
) {

    suspend fun insertIngredient(foodIngredients: List<FoodIngredient>) =
        foodIngredientDao.insert(foodIngredients)

    suspend fun getFoodIngredientByFoodId(foodId: Long) =
        foodIngredientDao.getIngredientByFoodId(foodId)

    suspend fun insertFoods(foodList: List<FoodEntity>) = foodDao.insert(foodList)

    suspend fun insertFoodCategories(categories: List<FoodCategoryEntity>) =
        foodCategoryDao.insert(categories)

    suspend fun insertFoodAreas(areas: List<FoodAreaEntity>) =
        foodAreaDao.insert(areas)

    fun getAllFood(): Flow<List<FoodEntity>> = foodDao.getAllFoods()

    fun getFavoriteFood(): Flow<List<FoodEntity>> = foodDao.getFavoriteFood()

    suspend fun setFavoriteFood(foods: FoodEntity) {
        foodDao.update(foods)
    }

    fun getFoodsByCategoryName(categoryName: String): Flow<List<FoodEntity>> =
        foodDao.getFoodByCategoryName(categoryName)

    fun getFoodsByAreaName(areaName: String): Flow<List<FoodEntity>> =
        foodDao.getFoodByAreaName(areaName)

    fun getAllCategory(): Flow<List<FoodCategoryEntity>> =
        foodCategoryDao.getAllCategories()

    fun getAllArea(): Flow<List<FoodAreaEntity>> =
        foodAreaDao.getAllArea()

    fun searchFood(query: String): Flow<List<FoodEntity>> =
        foodDao.searchFood(query)

    fun searchFoodWithFilterValue(
        query: String,
        categoryName: String,
        areaName: String,
        isFavorite: Boolean
    ): Flow<List<FoodEntity>> =
        foodDao.searchFoodWithFilterValue(query, categoryName, areaName, isFavorite)

    fun searchFoodJustCategoryAndArea(
        query: String,
        categoryName: String,
        areaName: String
    ): Flow<List<FoodEntity>> =
        foodDao.searchFoodJustCategoryAndArea(query, categoryName, areaName)

    fun searchFoodWithCategoryName(
        query: String,
        categoryName: String
    ): Flow<List<FoodEntity>> =
        foodDao.searchFoodWithCategoryName(query, categoryName)

    fun searchFoodWithAreaName(
        query: String,
        areaName: String
    ): Flow<List<FoodEntity>> =
        foodDao.searchFoodWithAreaName(query, areaName)

    fun searchFoodWithFavorite(
        query: String,
        isFavorite: Boolean
    ): Flow<List<FoodEntity>> =
        foodDao.searchFoodWithFavorite(query, isFavorite)

    fun filterFoodWithFullValue(categoryName: String, areaName: String, isFavorite: Boolean) =
        foodDao.filterFoodWithFullValue(categoryName, areaName, isFavorite)

    fun filterFoodJustCategoryAndArea(categoryName: String, areaName: String) =
        foodDao.filterFoodJustCategoryAndArea(categoryName, areaName)

    fun getFoodsWithFavorite(isFavorite: Boolean) =
        foodDao.getFoodsWithFavorite(isFavorite)
}