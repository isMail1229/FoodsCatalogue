package id.mailstudio.search.domain

sealed class FilterFoodUIState {
    object Loading : FilterFoodUIState()
    object Error : FilterFoodUIState()
    class Exists(val list: List<FoodUIFilterDetailModel>?) : FilterFoodUIState()
}

sealed class FoodCategoriesUIState {
    object Loading : FoodCategoriesUIState()
    object Error : FoodCategoriesUIState()
    class Exists(val list: List<FoodCategoryUIModel>?) : FoodCategoriesUIState()
}

sealed class FoodAreaUIState {
    object Loading : FoodAreaUIState()
    object Error : FoodAreaUIState()
    class Exists(val list: List<FoodAreaUIModel>?) : FoodAreaUIState()
}