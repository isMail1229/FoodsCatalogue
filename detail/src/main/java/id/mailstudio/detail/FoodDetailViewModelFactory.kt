package id.mailstudio.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.core.utils.coroutine.CoroutineConfig
import javax.inject.Inject

class FoodDetailViewModelFactory @Inject constructor(
    private val foodUseCase: FoodUseCase,
    private val coroutineConfig: CoroutineConfig
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FoodDetailViewModel::class.java) -> {
                FoodDetailViewModel(foodUseCase, coroutineConfig) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}