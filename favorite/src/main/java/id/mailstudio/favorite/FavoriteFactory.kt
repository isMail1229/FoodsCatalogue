package id.mailstudio.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mailstudio.core.domain.usecase.FoodUseCase
import javax.inject.Inject

class FavoriteFactory @Inject constructor(
    private val foodUseCase: FoodUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(foodUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}