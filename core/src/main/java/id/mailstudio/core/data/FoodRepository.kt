package id.mailstudio.core.data

import id.mailstudio.core.data.datasource.local.LocalDataSource
import id.mailstudio.core.data.datasource.remote.RemoteDataSource
import id.mailstudio.core.data.datasource.remote.network.ApiResponse
import id.mailstudio.core.data.datasource.remote.response.AreaItemResponse
import id.mailstudio.core.data.datasource.remote.response.CategoryItemResponse
import id.mailstudio.core.data.datasource.remote.response.MealsItemResponse
import id.mailstudio.core.domain.model.FoodAreaModel
import id.mailstudio.core.domain.model.FoodCategoryModel
import id.mailstudio.core.domain.model.FoodFilterModel
import id.mailstudio.core.domain.model.FoodIngredientModel
import id.mailstudio.core.domain.model.FoodModel
import id.mailstudio.core.domain.repository.IFoodRepository
import id.mailstudio.core.utils.toFoodEntity
import id.mailstudio.core.utils.toFoodIngredientModel
import id.mailstudio.core.utils.toListCategoryEntity
import id.mailstudio.core.utils.toListEntity
import id.mailstudio.core.utils.toListFoodAreaEntity
import id.mailstudio.core.utils.toListFoodAreaModel
import id.mailstudio.core.utils.toListFoodCategoryModel
import id.mailstudio.core.utils.toListFoodIngredient
import id.mailstudio.core.utils.toListFoodModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FoodRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IFoodRepository {
    override suspend fun getFoodIngredientsByFoodId(foodId: Long): List<FoodIngredientModel>? {
        return localDataSource.getFoodIngredientByFoodId(foodId)?.map {
            it.toFoodIngredientModel()
        }
    }

    override fun getFavFood(): Flow<List<FoodModel>> {
        return localDataSource.getFavoriteFood().map {
            it.toListFoodModel()
        }
    }

    override suspend fun setFavFood(foodModel: FoodModel) {
        val foodEntity = foodModel.toFoodEntity()
        localDataSource.setFavoriteFood(foodEntity)
    }

    override fun getAllFoodCategory(): Flow<Resource<List<FoodCategoryModel>>> {
        val dataCategory =
            object : NetworkBoundResource<List<FoodCategoryModel>, List<CategoryItemResponse>>() {
                override fun loadFromDB(): Flow<List<FoodCategoryModel>> {
                    return localDataSource.getAllCategory().map { categoryEntities ->
                        categoryEntities.toListFoodCategoryModel()
                    }
                }

                override fun shouldFetch(data: List<FoodCategoryModel>?): Boolean {
                    return data == null || data.isEmpty()
                }

                override suspend fun createCall(): Flow<ApiResponse<List<CategoryItemResponse>>> {
                    return remoteDataSource.getAllCategories()
                }

                override suspend fun saveCallResult(data: List<CategoryItemResponse>) {
                    val dataCategory = data.toListCategoryEntity()
                    localDataSource.insertFoodCategories(dataCategory)
                }

            }.asFlow()
        return dataCategory
    }

    override fun getAllFoodArea(): Flow<Resource<List<FoodAreaModel>>> {
        val dataFoodArea =
            object : NetworkBoundResource<List<FoodAreaModel>, List<AreaItemResponse>>() {
                override fun loadFromDB(): Flow<List<FoodAreaModel>> {
                    return localDataSource.getAllArea().map {
                        it.toListFoodAreaModel()
                    }
                }

                override fun shouldFetch(data: List<FoodAreaModel>?): Boolean {
                    return data == null || data.isEmpty()
                }

                override suspend fun createCall(): Flow<ApiResponse<List<AreaItemResponse>>> {
                    return remoteDataSource.getAllArea()
                }

                override suspend fun saveCallResult(data: List<AreaItemResponse>) {
                    val dataFoodArea = data.toListFoodAreaEntity()
                    localDataSource.insertFoodAreas(dataFoodArea)
                }

            }.asFlow()

        return dataFoodArea
    }

    override fun getFoodByCategoryName(categoryName: String): Flow<List<FoodModel>> {
        return localDataSource.getFoodsByCategoryName(categoryName).map {
            it.toListFoodModel()
        }
    }

    override fun getFoodByArea(area: String): Flow<List<FoodModel>> {
        return localDataSource.getFoodsByAreaName(area).map {
            it.toListFoodModel()
        }
    }

    override fun getAllFoodByFirstLetter(): Flow<Resource<List<FoodModel>>> {
        val data = object : NetworkBoundResource<List<FoodModel>, List<MealsItemResponse>>() {
            override fun loadFromDB(): Flow<List<FoodModel>> {
                return localDataSource.getAllFood().map { listFoodEntity ->
                    listFoodEntity.toListFoodModel()
                }
            }

            override fun shouldFetch(data: List<FoodModel>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MealsItemResponse>>> {
                return remoteDataSource.getMealByFirstLetter()
            }

            override suspend fun saveCallResult(data: List<MealsItemResponse>) {
                val listFood = data.toListEntity()
                val listFoodIngredient = data.toListFoodIngredient()
                localDataSource.insertFoods(listFood)
                localDataSource.insertIngredient(listFoodIngredient)
            }

        }.asFlow()

        return data
    }

    override fun searchFoodWithFilterValue(
        query: String,
        foodFilterModel: FoodFilterModel
    ): Flow<List<FoodModel>> {
        return when {
            query.isNotBlank() && foodFilterModel.category?.name != null && foodFilterModel.area?.name != null && foodFilterModel.isFavorite != null -> {
                localDataSource.searchFoodWithFilterValue(
                    query,
                    foodFilterModel.category.name,
                    foodFilterModel.area.name,
                    foodFilterModel.isFavorite
                ).map {
                    it.toListFoodModel()
                }
            }
            query.isNotBlank() && foodFilterModel.category?.name != null && foodFilterModel.area?.name != null && foodFilterModel.isFavorite == null -> {
                localDataSource.searchFoodJustCategoryAndArea(
                    query,
                    foodFilterModel.category.name,
                    foodFilterModel.area.name,
                ).map {
                    it.toListFoodModel()
                }
            }
            query.isNotBlank() && foodFilterModel.category?.name != null -> {
                localDataSource.searchFoodWithCategoryName(
                    query,
                    foodFilterModel.category.name
                ).map {
                    it.toListFoodModel()
                }
            }
            query.isNotBlank() && foodFilterModel.area?.name != null -> {
                localDataSource.searchFoodWithAreaName(
                    query,
                    foodFilterModel.area.name
                ).map {
                    it.toListFoodModel()
                }
            }
            query.isNotBlank() && foodFilterModel.isFavorite != null -> {
                localDataSource.searchFoodWithFavorite(
                    query,
                    foodFilterModel.isFavorite
                ).map {
                    it.toListFoodModel()
                }
            }
            else -> {
                localDataSource.searchFood(query).map {
                    it.toListFoodModel()
                }
            }
        }
    }

    override fun filterFood(
        categoryName: String?,
        areaName: String?,
        isFavorite: Boolean?
    ): Flow<List<FoodModel>> {
        return when {
            !categoryName.isNullOrBlank() && !areaName.isNullOrBlank() && isFavorite != null -> {
                localDataSource.filterFoodWithFullValue(categoryName, areaName, isFavorite).map {
                    it.toListFoodModel()
                }
            }
            !categoryName.isNullOrBlank() && !areaName.isNullOrBlank() && isFavorite == null -> {
                localDataSource.filterFoodJustCategoryAndArea(categoryName, areaName).map {
                    it.toListFoodModel()
                }
            }
            !categoryName.isNullOrBlank() -> {
                localDataSource.getFoodsByCategoryName(categoryName).map {
                    it.toListFoodModel()
                }
            }
            !areaName.isNullOrBlank() -> {
                localDataSource.getFoodsByAreaName(areaName).map {
                    it.toListFoodModel()
                }
            }
            isFavorite != null -> {
                localDataSource.getFoodsWithFavorite(isFavorite).map {
                    it.toListFoodModel()
                }
            }
            else -> {
                localDataSource.getAllFood().map {
                    it.toListFoodModel()
                }
            }
        }
    }
}