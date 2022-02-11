package id.mailstudio.core.domain.model

data class FoodModel(
    val foodId: String,
    val foodName: String,
    val foodInstruction: String,
    val foodArea: String,
    val foodCategory: String,
    val foodImage: String,
    val foodSource: String,
    val foodTags: String? = null,
    val isFavorite: Boolean = false
)