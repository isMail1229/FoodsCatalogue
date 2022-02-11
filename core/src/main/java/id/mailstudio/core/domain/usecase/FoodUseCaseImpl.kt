package id.mailstudio.core.domain.usecase

import id.mailstudio.core.data.Resource
import id.mailstudio.core.domain.model.FoodAreaModel
import id.mailstudio.core.domain.model.FoodCategoryModel
import id.mailstudio.core.domain.model.FoodFilterModel
import id.mailstudio.core.domain.model.FoodIngredientModel
import id.mailstudio.core.domain.model.FoodModel
import id.mailstudio.core.domain.repository.IFoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodUseCaseImpl @Inject constructor(
    private val foodRepository: IFoodRepository
) : FoodUseCase {
    override suspend fun getListIngredientByFoodId(foodId: Long): List<FoodIngredientModel>? {
        return foodRepository.getFoodIngredientsByFoodId(foodId)
    }

    override fun getAllFoodByAlphabet(): Flow<Resource<List<FoodModel>>> {
        return foodRepository.getAllFoodByFirstLetter()
    }

    override fun getAllCategories(): Flow<Resource<List<FoodCategoryModel>>> {
        return foodRepository.getAllFoodCategory()
    }

    override fun getAllAreas(): Flow<Resource<List<FoodAreaModel>>> {
        return foodRepository.getAllFoodArea()
    }

    override fun getFavoriteFood(): Flow<List<FoodModel>> {
        return foodRepository.getFavFood()
    }

    override suspend fun setFavoriteFood(foodModel: FoodModel) {
        foodRepository.setFavFood(foodModel)
    }

    override fun getFoodByCategoryName(categoryName: String): Flow<List<FoodModel>> {
        return foodRepository.getFoodByCategoryName(categoryName)
    }

    override fun getFoodByArea(areaName: String): Flow<List<FoodModel>> {
        return foodRepository.getFoodByArea(areaName)
    }

    override fun searchOrFilterFoods(
        query: String,
        foodFilterModel: FoodFilterModel
    ): Flow<List<FoodModel>> {
        return if (query.isEmpty() || query.isBlank()) {
            foodRepository.filterFood(
                foodFilterModel.category?.name,
                foodFilterModel.area?.name,
                foodFilterModel.isFavorite
            )
        } else {
            foodRepository.searchFoodWithFilterValue(query, foodFilterModel)
        }
    }
}