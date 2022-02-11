package id.mailstudio.foodcatalogue.domain

sealed class FoodModelUIState {
    object Loading : FoodModelUIState()
    object Error : FoodModelUIState()
    class Exists(val list: List<FoodUIModel>?) : FoodModelUIState()
}