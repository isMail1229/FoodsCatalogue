package id.mailstudio.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.core.utils.preferences.FoodSharedPreference
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val foodUseCase: FoodUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FoodFilterSearchViewModel::class.java) -> {
                FoodFilterSearchViewModel(foodUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}