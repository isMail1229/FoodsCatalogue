package id.mailstudio.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.mailstudio.core.domain.model.FoodIngredientModel
import id.mailstudio.core.domain.usecase.FoodUseCase
import id.mailstudio.foodcatalogue.domain.FoodIngredientUIModel
import id.mailstudio.foodcatalogue.domain.FoodUIModel
import id.mailstudio.foodcatalogue.domain.toFoodIngredientUIModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FoodDetailViewModelUnitTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var viewModel: FoodDetailViewModel

    @Mock
    lateinit var useCase: FoodUseCase

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var foodObserver: Observer<FoodUIModel?>

    @Mock
    private lateinit var isFavoriteObserver: Observer<Boolean>

    @Mock
    private lateinit var foodIngredientsObserver: Observer<List<FoodIngredientUIModel>>

    private val fakeFoodUIModel = FoodUIModel(
        "1",
        "",
        "",
        "",
        "",
        "",
        true
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel = FoodDetailViewModel(
            useCase,
            FakeCoroutineConfigImpl(testCoroutineDispatcher)
        )

        viewModel.foodModel.observeForever(foodObserver)
        viewModel.foodIngredient.observeForever(foodIngredientsObserver)
        viewModel.isFavorite.observeForever(isFavoriteObserver)
    }

    @Test
    fun `when setFoodFromParcelable() when invoke`() {
        val fakeIngredientUIModel = FoodIngredientModel(
            "1",
            ""
        )
        runBlockingTest(testCoroutineDispatcher) {
            Mockito.`when`(useCase.getListIngredientByFoodId(1L)).thenReturn(
                listOf(fakeIngredientUIModel)
            )
        }

        viewModel.setFoodFromParcelable(fakeFoodUIModel)

        Mockito.verify(foodObserver).onChanged(fakeFoodUIModel)
        Mockito.verify(isFavoriteObserver).onChanged(fakeFoodUIModel.isFavorite)
        Mockito.verify(foodIngredientsObserver)
            .onChanged(listOf(fakeIngredientUIModel.toFoodIngredientUIModel()))
    }

    @After
    fun tearDown() {
        viewModel.foodModel.removeObserver(foodObserver)
        viewModel.foodIngredient.removeObserver(foodIngredientsObserver)
        viewModel.isFavorite.removeObserver(isFavoriteObserver)
    }
}