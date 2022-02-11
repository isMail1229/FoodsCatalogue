package id.mailstudio.core.domain.usecase

import id.mailstudio.core.data.Resource
import id.mailstudio.core.domain.model.FoodAreaModel
import id.mailstudio.core.domain.model.FoodCategoryModel
import id.mailstudio.core.domain.model.FoodFilterModel
import id.mailstudio.core.domain.model.FoodIngredientModel
import id.mailstudio.core.domain.model.FoodModel
import kotlinx.coroutines.flow.Flow


interface FoodUseCase {

    suspend fun getListIngredientByFoodId(foodId: Long): List<FoodIngredientModel>?

    fun getAllFoodByAlphabet(): Flow<Resource<List<FoodModel>>>

    fun getAllCategories(): Flow<Resource<List<FoodCategoryModel>>>

    fun getAllAreas(): Flow<Resource<List<FoodAreaModel>>>

    fun getFavoriteFood(): Flow<List<FoodModel>>

    suspend fun setFavoriteFood(foodModel: FoodModel)

    fun getFoodByCategoryName(categoryName: String): Flow<List<FoodModel>>

    fun getFoodByArea(areaName: String): Flow<List<FoodModel>>

    fun searchOrFilterFoods(query: String, foodFilterModel: FoodFilterModel): Flow<List<FoodModel>>
}