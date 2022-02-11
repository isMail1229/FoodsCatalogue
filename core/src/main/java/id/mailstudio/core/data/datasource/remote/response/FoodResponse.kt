package id.mailstudio.core.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class FoodResponse<T>(
    @field:SerializedName("meals")
    val meals: List<T>? = null
)
