package id.mailstudio.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.core.utils.coroutine.CoroutineConfig
import id.mailstudio.foodcatalogue.domain.FoodIngredientUIModel
import id.mailstudio.foodcatalogue.domain.FoodUIModel
import id.mailstudio.foodcatalogue.domain.toFoodModel
import id.mailstudio.foodcatalogue.domain.toListFoodIngredientUIModel
import kotlinx.coroutines.launch

class FoodDetailViewModel(
    private val foodUseCase: FoodUseCase,
    private val coroutineConfig: CoroutineConfig
) : ViewModel() {

    private var _foodModel = MutableLiveData<FoodUIModel?>()
    val foodModel: LiveData<FoodUIModel?>
        get() = _foodModel

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _foodIngredients = MutableLiveData<List<FoodIngredientUIModel>>()
    val foodIngredient: LiveData<List<FoodIngredientUIModel>>
        get() = _foodIngredients

    fun setFoodFromParcelable(foodModelParcelable: FoodUIModel?) {
        if (_foodModel.value == null) {
            _foodModel.postValue(foodModelParcelable)
            _isFavorite.postValue(foodModelParcelable?.isFavorite ?: false)
            viewModelScope.launch(coroutineConfig.ioDispatcher()) {
                val dataIngredient = foodUseCase.getListIngredientByFoodId(
                    foodModelParcelable?.foodId?.toLong() ?: 0L
                )
                _foodIngredients.postValue(dataIngredient?.toListFoodIngredientUIModel())
            }
        }
    }

    fun setFavoriteFood(isFavorite: Boolean, foodUIModel: FoodUIModel?) {
        _isFavorite.value = isFavorite
        viewModelScope.launch(coroutineConfig.ioDispatcher()) {
            if (foodUIModel != null)
                foodUseCase.setFavoriteFood(foodUIModel.toFoodModel(isFavorite))
        }
    }
}