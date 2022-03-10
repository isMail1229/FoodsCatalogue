package id.mailstudio.foodcatalogue.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import id.mailstudio.core.data.Resource
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.core.utils.Constants
import id.mailstudio.core.utils.preferences.FoodSharedPreference
import id.mailstudio.foodcatalogue.domain.FoodModelUIState
import id.mailstudio.foodcatalogue.domain.toListFoodUIModel
import javax.inject.Inject

@HiltViewModel
class FoodHomeViewModel @Inject constructor(
    foodUseCase: FoodUseCase,
    private val foodSharedPreference: FoodSharedPreference
) : ViewModel() {

    private val _currentTheme = MutableLiveData<Int>()
    val currentTheme: LiveData<Int>
        get() = _currentTheme

    val foods = foodUseCase.getAllFoodByAlphabet().asLiveData().map {
        when (it) {
            is Resource.Loading -> FoodModelUIState.Loading
            is Resource.Error -> FoodModelUIState.Error
            is Resource.Success -> FoodModelUIState.Exists(it.data?.toListFoodUIModel())
        }
    }

    init {
        val valueTheme = foodSharedPreference.getInt(Constants.PREF_THEME, 0)
        _currentTheme.postValue(valueTheme)
    }

    fun setTheme(value: Int) {
        _currentTheme.value = value
        foodSharedPreference.putInt(Constants.PREF_THEME, value)
    }
}