package id.mailstudio.core.domain.model

data class FoodFilterModel(
    val category: FoodCategoryModel?,
    val area: FoodAreaModel?,
    val isFavorite: Boolean? = null
)