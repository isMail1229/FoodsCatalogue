package id.mailstudio.core.domain.repository

import id.mailstudio.core.data.Resource
import id.mailstudio.core.domain.model.FoodAreaModel
import id.mailstudio.core.domain.model.FoodCategoryModel
import id.mailstudio.core.domain.model.FoodFilterModel
import id.mailstudio.core.domain.model.FoodIngredientModel
import id.mailstudio.core.domain.model.FoodModel
import kotlinx.coroutines.flow.Flow


interface IFoodRepository {

    suspend fun getFoodIngredientsByFoodId(foodId: Long): List<FoodIngredientModel>?

    fun getFavFood(): Flow<List<FoodModel>>

    suspend fun setFavFood(foodModel: FoodModel)

    fun getAllFoodCategory(): Flow<Resource<List<FoodCategoryModel>>>

    fun getAllFoodArea(): Flow<Resource<List<FoodAreaModel>>>

    fun getFoodByCategoryName(categoryName: String): Flow<List<FoodModel>>

    fun getFoodByArea(area: String): Flow<List<FoodModel>>

    fun getAllFoodByFirstLetter(): Flow<Resource<List<FoodModel>>>

    fun searchFoodWithFilterValue(
        query: String,
        foodFilterModel: FoodFilterModel
    ): Flow<List<FoodModel>>

    fun filterFood(
        categoryName: String? = "",
        areaName: String? = "",
        isFavorite: Boolean? = null
    ): Flow<List<FoodModel>>
}