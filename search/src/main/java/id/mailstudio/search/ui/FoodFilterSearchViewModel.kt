package id.mailstudio.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import id.mailstudio.core.data.Resource
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.foodcatalogue.domain.toListFoodUIModel
import id.mailstudio.search.domain.FoodAreaUIModel
import id.mailstudio.search.domain.FoodAreaUIState
import id.mailstudio.search.domain.FoodCategoriesUIState
import id.mailstudio.search.domain.FoodCategoryUIModel
import id.mailstudio.search.domain.FoodFilterUIModel
import id.mailstudio.search.domain.toFoodFilterModel
import id.mailstudio.search.domain.toListFoodAreaUIModel
import id.mailstudio.search.domain.toListFoodCategoryUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map


class FoodFilterSearchViewModel(
    private val foodUseCase: FoodUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _foodFilterUIModel = MutableStateFlow(FoodFilterUIModel(null, null, null))

    private val _filterFood = MutableLiveData<FoodFilterUIModel>()
    val filterFood: LiveData<FoodFilterUIModel>
        get() = _filterFood

    private val _selectedCategory = MutableLiveData<FoodCategoryUIModel>()
    val selectedCategory: LiveData<FoodCategoryUIModel>
        get() = _selectedCategory

    private val _selectedArea = MutableLiveData<FoodAreaUIModel>()
    val selectedArea: LiveData<FoodAreaUIModel>
        get() = _selectedArea

    private val _statusFavorite = MutableLiveData<Int>()
    val statusFavorite: LiveData<Int>
        get() = _statusFavorite

    var isChange: Boolean = false

    val categories = foodUseCase.getAllCategories().asLiveData().map {
        when (it) {
            is Resource.Loading -> FoodCategoriesUIState.Loading
            is Resource.Error -> FoodCategoriesUIState.Error
            is Resource.Success -> FoodCategoriesUIState.Exists(it.data?.toListFoodCategoryUIModel())
        }
    }

    val areas = foodUseCase.getAllAreas().asLiveData().map {
        when (it) {
            is Resource.Loading -> FoodAreaUIState.Loading
            is Resource.Error -> FoodAreaUIState.Error
            is Resource.Success -> FoodAreaUIState.Exists(it.data?.toListFoodAreaUIModel())
        }
    }

    fun setFromParcelable(filterUIModel: FoodFilterUIModel?) {
        _filterFood.value = filterUIModel ?: FoodFilterUIModel(null, null, null)
    }

    fun searchOrFilterFood(query: String, foodFilterUIModel: FoodFilterUIModel?) {
        _query.value = query
        _foodFilterUIModel.value = foodFilterUIModel ?: FoodFilterUIModel(null, null, null)
    }

    @ExperimentalCoroutinesApi
    val filterSearch = combine(
        _query,
        _foodFilterUIModel
    ) { query, filterDomain ->
        Pair(query, filterDomain)
    }
        .distinctUntilChanged()
        .flatMapLatest {
            foodUseCase.searchOrFilterFoods(it.first, it.second.toFoodFilterModel())
                .map { listFood ->
                    listFood.toListFoodUIModel()
                }
        }.asLiveData()

    fun selectCategory(category: FoodCategoryUIModel) {
        _selectedCategory.value = category
    }

    fun selectArea(area: FoodAreaUIModel) {
        _selectedArea.value = area
    }

    fun setFavorite(id: Int) {
        _statusFavorite.value = id
    }

    fun getDefaultCategory(name: String) =
        FoodCategoryUIModel(0, name)

    fun getDefaultArea(name: String) =
        FoodAreaUIModel(0, name)
}