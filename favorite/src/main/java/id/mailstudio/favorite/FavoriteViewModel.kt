package id.mailstudio.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.mailstudio.core.domain.usecase.FoodUseCase

class FavoriteViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    val favorite = foodUseCase.getFavoriteFood().asLiveData()
}