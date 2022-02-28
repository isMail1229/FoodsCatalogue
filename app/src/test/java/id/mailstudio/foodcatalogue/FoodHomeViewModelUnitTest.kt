package id.mailstudio.foodcatalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.mailstudio.core.data.Resource
import id.mailstudio.core.domain.model.FoodModel
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.core.utils.Constants
import id.mailstudio.core.utils.preferences.FoodSharedPreference
import id.mailstudio.foodcatalogue.ui.home.FoodHomeViewModel
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FoodHomeViewModelUnitTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var viewModel: FoodHomeViewModel

    @Mock
    lateinit var useCase: FoodUseCase

    @Mock
    lateinit var foodSharedPreference: FoodSharedPreference

    @Mock
    private lateinit var selectedTheme: Observer<Int>

    @Before
    fun setup() {
        val fakeModel = FoodModel(
            "1",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(useCase.getAllFoodByAlphabet())
            .thenReturn(flowOf(Resource.Success(listOf(fakeModel))))

        viewModel = FoodHomeViewModel(
            useCase,
            foodSharedPreference
        )
        viewModel.currentTheme.observeForever(selectedTheme)
    }

    @Test
    fun `when setTheme() is invoked`() {

        viewModel.setTheme(1)

        Mockito.verify(selectedTheme).onChanged(1)
        Mockito.verify(foodSharedPreference).putInt(Constants.PREF_THEME, 1)
    }

    @After
    fun tearDown() {
        viewModel.currentTheme.removeObserver(selectedTheme)
    }


}