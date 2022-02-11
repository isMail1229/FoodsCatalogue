package id.mailstudio.core.data.datasource.remote.network

import id.mailstudio.core.data.datasource.remote.response.AreaItemResponse
import id.mailstudio.core.data.datasource.remote.response.CategoryItemResponse
import id.mailstudio.core.data.datasource.remote.response.FoodResponse
import id.mailstudio.core.data.datasource.remote.response.MealsItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodAPIService {

    @GET("search.php")
    suspend fun getAllMealByFirstLetter(
        @Query("f") firstLetter: String
    ): FoodResponse<MealsItemResponse>

    @GET("list.php?a=list")
    suspend fun getAllArea(): FoodResponse<AreaItemResponse>

    @GET("list.php?c=list")
    suspend fun getAllCategories(): FoodResponse<CategoryItemResponse>
}