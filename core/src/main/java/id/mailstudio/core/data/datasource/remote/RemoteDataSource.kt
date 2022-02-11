package id.mailstudio.core.data.datasource.remote

import android.util.Log
import id.mailstudio.core.data.datasource.remote.network.ApiResponse
import id.mailstudio.core.data.datasource.remote.network.FoodAPIService
import id.mailstudio.core.data.datasource.remote.response.AreaItemResponse
import id.mailstudio.core.data.datasource.remote.response.CategoryItemResponse
import id.mailstudio.core.data.datasource.remote.response.MealsItemResponse
import id.mailstudio.core.utils.coroutine.CoroutineConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val foodAPIService: FoodAPIService,
    private val coroutineConfig: CoroutineConfig
) {

    suspend fun getMealByFirstLetter(): Flow<ApiResponse<List<MealsItemResponse>>> {
        return flow {
            try {
                val listMeals = arrayListOf<MealsItemResponse>()
                var firstLetter = 'a'
                while (firstLetter <= 'z') {
                    val apiResponse = foodAPIService.getAllMealByFirstLetter(firstLetter.toString())
                    val data = apiResponse.meals
                    if (!data.isNullOrEmpty()) {
                        listMeals.addAll(data)
                    }
                    firstLetter++
                }
                if (!listMeals.isNullOrEmpty()) {
                    emit(ApiResponse.Success(listMeals))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "getMealByFirstLetter: $e")
            }
        }.flowOn(coroutineConfig.ioDispatcher())
    }

    suspend fun getAllArea(): Flow<ApiResponse<List<AreaItemResponse>>> {
        return flow {
            try {
                val response = foodAPIService.getAllArea()
                val data = response.meals
                if (!data.isNullOrEmpty()) {
                    emit(ApiResponse.Success(data))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "getAllArea: $e")
            }
        }.flowOn(coroutineConfig.ioDispatcher())
    }

    suspend fun getAllCategories(): Flow<ApiResponse<List<CategoryItemResponse>>> {
        return flow {
            try {
                val response = foodAPIService.getAllCategories()
                val data = response.meals
                if (!data.isNullOrEmpty()) {
                    emit(ApiResponse.Success(data))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "getAllCategories: $e")
            }
        }.flowOn(coroutineConfig.ioDispatcher())
    }
}