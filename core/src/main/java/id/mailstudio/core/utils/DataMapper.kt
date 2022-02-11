package id.mailstudio.core.utils

import id.mailstudio.core.data.datasource.local.entity.FoodAreaEntity
import id.mailstudio.core.data.datasource.local.entity.FoodCategoryEntity
import id.mailstudio.core.data.datasource.local.entity.FoodEntity
import id.mailstudio.core.data.datasource.local.entity.FoodIngredient
import id.mailstudio.core.data.datasource.remote.response.AreaItemResponse
import id.mailstudio.core.data.datasource.remote.response.CategoryItemResponse
import id.mailstudio.core.data.datasource.remote.response.MealsItemResponse
import id.mailstudio.core.domain.model.FoodAreaModel
import id.mailstudio.core.domain.model.FoodCategoryModel
import id.mailstudio.core.domain.model.FoodIngredientModel
import id.mailstudio.core.domain.model.FoodModel


fun List<FoodAreaEntity>.toListFoodAreaModel(): List<FoodAreaModel> {
    val data = this.map {
        it.toFoodAreaModel()
    }
    return data
}

fun FoodAreaEntity.toFoodAreaModel(): FoodAreaModel {
    return FoodAreaModel(
        id = this.id,
        name = this.name
    )
}

fun List<AreaItemResponse>.toListFoodAreaEntity(): List<FoodAreaEntity> {
    val data = this.map {
        it.toFoodAreaEntity()
    }
    return data
}

fun AreaItemResponse.toFoodAreaEntity(): FoodAreaEntity {
    return FoodAreaEntity(
        id = 0,
        name = this.strArea ?: ""
    )
}

fun List<FoodCategoryEntity>.toListFoodCategoryModel(): List<FoodCategoryModel> {
    val data = this.map {
        it.toCategoryModel()
    }
    return data
}

fun FoodCategoryEntity.toCategoryModel(): FoodCategoryModel {
    return FoodCategoryModel(
        id = this.id,
        name = this.name
    )
}

fun List<CategoryItemResponse>.toListCategoryEntity(): List<FoodCategoryEntity> {
    val data = this.map {
        it.toCategoryEntity()
    }
    return data
}

fun CategoryItemResponse.toCategoryEntity(): FoodCategoryEntity {
    return FoodCategoryEntity(
        id = 0,
        name = this.strCategory ?: ""
    )
}

fun FoodModel.toFoodEntity(): FoodEntity {
    return FoodEntity(
        foodId = this.foodId,
        foodName = this.foodName,
        foodInstruction = this.foodInstruction,
        foodImage = this.foodImage,
        foodCategoryName = this.foodCategory,
        foodArea = this.foodArea,
        foodTags = this.foodTags,
        foodSource = this.foodSource,
        isFavorite = this.isFavorite
    )
}

fun List<MealsItemResponse>.toListEntity(): List<FoodEntity> {
    val data = this.map {
        it.toFoodEntity()
    }
    return data
}

fun MealsItemResponse.toFoodEntity(): FoodEntity {
    return FoodEntity(
        foodId = this.idMeal ?: "",
        foodName = this.strMeal ?: "",
        foodInstruction = this.strInstructions ?: "",
        foodImage = this.strMealThumb ?: "",
        foodCategoryName = this.strCategory ?: "",
        foodArea = this.strArea ?: "",
        foodTags = this.strTags ?: "",
        foodSource = this.strSource ?: ""
    )
}

fun List<MealsItemResponse>.toListFoodIngredient(): List<FoodIngredient> {
    val data = mutableListOf<FoodIngredient>()
    this.forEach {
        val dataIngredientAndMeasure = processFoodIngredientAndMeasure(
            it.strIngredient1 ?: "",
            it.strIngredient2 ?: "",
            it.strIngredient3 ?: "",
            it.strIngredient4 ?: "",
            it.strIngredient5 ?: "",
            it.strIngredient6 ?: "",
            it.strIngredient7 ?: "",
            it.strIngredient8 ?: "",
            it.strIngredient9 ?: "",
            it.strIngredient10 ?: "",
            it.strIngredient11 ?: "",
            it.strIngredient12 ?: "",
            it.strIngredient13 ?: "",
            it.strIngredient14 ?: "",
            it.strIngredient15 ?: "",
            it.strIngredient16 ?: "",
            it.strIngredient17 ?: "",
            it.strIngredient18 ?: "",
            it.strIngredient19 ?: "",
            it.strIngredient20 ?: "",
            it.strMeasure1 ?: "",
            it.strMeasure2 ?: "",
            it.strMeasure3 ?: "",
            it.strMeasure4 ?: "",
            it.strMeasure5 ?: "",
            it.strMeasure6 ?: "",
            it.strMeasure7 ?: "",
            it.strMeasure8 ?: "",
            it.strMeasure9 ?: "",
            it.strMeasure10 ?: "",
            it.strMeasure11 ?: "",
            it.strMeasure12 ?: "",
            it.strMeasure13 ?: "",
            it.strMeasure14 ?: "",
            it.strMeasure15 ?: "",
            it.strMeasure16 ?: "",
            it.strMeasure17 ?: "",
            it.strMeasure18 ?: "",
            it.strMeasure19 ?: "",
            it.strMeasure20 ?: ""
        )
        val dataIngredient = dataIngredientAndMeasure.map { foodMeasureAndIngredient ->
            FoodIngredient(
                id = 0L,
                foodId = it.idMeal?.toLong() ?: 0L,
                foodIngredient = foodMeasureAndIngredient.foodIngredient,
                foodMeasurement = foodMeasureAndIngredient.foodMeasure
            )
        }
        data.addAll(dataIngredient)
    }
    return data
}

fun List<FoodEntity>.toListFoodModel(): List<FoodModel> {
    val data = this.map {
        it.toFoodModel()
    }
    return data
}

fun FoodEntity.toFoodModel(): FoodModel {
    return FoodModel(
        foodId = this.foodId,
        foodName = this.foodName,
        foodInstruction = this.foodInstruction,
        foodArea = this.foodArea,
        foodCategory = this.foodCategoryName,
        foodImage = this.foodImage,
        foodSource = this.foodSource,
        foodTags = this.foodTags,
        isFavorite = this.isFavorite
    )
}

fun List<FoodIngredient>.toListFoodIngredientModel(): List<FoodIngredientModel> {
    val data = this.map {
        it.toFoodIngredientModel()
    }
    return data
}

fun FoodIngredient.toFoodIngredientModel(): FoodIngredientModel {
    return FoodIngredientModel(
        foodIngredient = this.foodIngredient ?: "",
        foodMeasure = this.foodMeasurement ?: ""
    )
}

private fun processFoodIngredientAndMeasure(
    ingredient1: String,
    ingredient2: String,
    ingredient3: String,
    ingredient4: String,
    ingredient5: String,
    ingredient6: String,
    ingredient7: String,
    ingredient8: String,
    ingredient9: String,
    ingredient10: String,
    ingredient11: String,
    ingredient12: String,
    ingredient13: String,
    ingredient14: String,
    ingredient15: String,
    ingredient16: String,
    ingredient17: String,
    ingredient18: String,
    ingredient19: String,
    ingredient20: String,
    measure1: String,
    measure2: String,
    measure3: String,
    measure4: String,
    measure5: String,
    measure6: String,
    measure7: String,
    measure8: String,
    measure9: String,
    measure10: String,
    measure11: String,
    measure12: String,
    measure13: String,
    measure14: String,
    measure15: String,
    measure16: String,
    measure17: String,
    measure18: String,
    measure19: String,
    measure20: String,
): List<FoodIngredientModel> {
    val listFoodIngredientModel = arrayListOf<FoodIngredientModel>()
    if (ingredient1.isNotBlank() && measure1.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient1, measure1))
    if (ingredient2.isNotBlank() && measure2.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient2, measure2))
    if (ingredient3.isNotBlank() && measure3.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient3, measure3))
    if (ingredient4.isNotBlank() && measure4.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient4, measure4))
    if (ingredient5.isNotBlank() && measure5.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient5, measure5))
    if (ingredient6.isNotBlank() && measure6.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient6, measure6))
    if (ingredient7.isNotBlank() && measure7.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient7, measure7))
    if (ingredient8.isNotBlank() && measure8.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient8, measure8))
    if (ingredient9.isNotBlank() && measure9.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient9, measure9))
    if (ingredient10.isNotBlank() && measure10.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient10, measure10))
    if (ingredient11.isNotBlank() && measure11.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient11, measure11))
    if (ingredient12.isNotBlank() && measure12.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient12, measure12))
    if (ingredient13.isNotBlank() && measure13.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient13, measure13))
    if (ingredient14.isNotBlank() && measure14.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient14, measure14))
    if (ingredient15.isNotBlank() && measure15.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient15, measure15))
    if (ingredient16.isNotBlank() && measure16.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient16, measure16))
    if (ingredient17.isNotBlank() && measure17.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient17, measure17))
    if (ingredient18.isNotBlank() && measure18.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient18, measure18))
    if (ingredient19.isNotBlank() && measure19.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient19, measure19))
    if (ingredient20.isNotBlank() && measure20.isNotBlank())
        listFoodIngredientModel.add(FoodIngredientModel(ingredient20, measure20))

    return listFoodIngredientModel
}