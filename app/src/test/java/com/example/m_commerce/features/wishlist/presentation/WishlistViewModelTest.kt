import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.domain.usecases.AddToWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import com.example.m_commerce.features.product.presentation.SnackBarMessage
import com.example.m_commerce.features.wishlist.domain.usecases.DeleteFromWishlistUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.GetWishlistUseCase
import com.example.m_commerce.features.wishlist.presentation.WishlistUiState
import com.example.m_commerce.features.wishlist.presentation.WishlistViewModel
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

@OptIn(ExperimentalCoroutinesApi::class)
class WishlistViewModelTest {

    private val getWishlist = mockk<GetWishlistUseCase>(relaxed = true)
    private val getProductById = mockk<GetProductByIdUseCase>(relaxed = true)
    private val deleteFromWishlist = mockk<DeleteFromWishlistUseCase>(relaxed = true)
    private val addToWishlist = mockk<AddToWishlistUseCase>(relaxed = true)

    private lateinit var viewModel: WishlistViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance().currentUser } returns mockk(relaxed = true)

        viewModel =
            WishlistViewModel(getWishlist, getProductById, deleteFromWishlist, addToWishlist, mockk(relaxed = true))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    private fun createFakeProduct(id: String) = Product(
        id = id,
        title = "Product $id",
        description = "Desc",
        images = emptyList(),
        price = "20",
        category = "Accessories",
        currencyCode = "USD",
        colors = emptyList(),
        sizes = emptyList(),
        variants = emptyList(),
        brand = "Vans"
    )

    @Test
    fun `init emits Guest if no user`() {
        // Given
        every { FirebaseAuth.getInstance().currentUser } returns null

        // When
        viewModel =
            WishlistViewModel(getWishlist, getProductById, deleteFromWishlist, addToWishlist, mockk(relaxed = true))

        // Then
        assertThat(viewModel.uiState.value, `is`(WishlistUiState.Guest))
    }

    @Test
    fun `getProducts emits Success when wishlist is not empty`() = runTest {
        // given
        val wishlistIds = listOf("p1", "p2")
        val product1 = createFakeProduct("p1")
        val product2 = createFakeProduct("p2")

        coEvery { getWishlist() } returns flowOf(wishlistIds)
        coEvery { getProductById("p1") } returns flowOf(product1)
        coEvery { getProductById("p2") } returns flowOf(product2)

        // when
        viewModel.getProducts()
        advanceUntilIdle()

        val expectedState = WishlistUiState.Success(listOf(product1, product2))
        assertThat(viewModel.uiState.value, `is`(expectedState))
    }

    @Test
    fun `getProducts emits Empty when wishlist is empty`() = runTest {
        // given
        coEvery { getWishlist() } returns flowOf(emptyList())

        // when
        viewModel.getProducts()
        advanceUntilIdle()

        // then
        assertThat(viewModel.uiState.value, `is`(WishlistUiState.Empty))
    }

    @Test
    fun `deleteProductFromWishlist emits SnackBarMessage on success`() = runTest {
        // given
        val productId = "p123"
        val successMsg = "Removed successfully"

        coEvery { deleteFromWishlist(productId) } returns flowOf(successMsg)
        //coEvery { addToWishlist(productId) } returns flowOf("")

        val emittedMessages = mutableListOf<SnackBarMessage>()
        val job = launch {
            viewModel.message.collect { emittedMessages.add(it) }
        }

        // when
        viewModel.deleteProductFromWishlist(productId)
        advanceUntilIdle()

        // then
        job.cancel()

        assertThat(emittedMessages.any { it.message.contains(successMsg) }, `is`(true))
    }
}
