package com.example.m_commerce.features.coupon.data.remote

import com.example.m_commerce.features.coupon.domain.entity.Coupon
import com.example.m_commerce.features.coupon.domain.entity.DiscountCodeDto
import com.example.m_commerce.features.coupon.domain.entity.PriceRuleDto
import com.example.m_commerce.features.coupon.domain.entity.PriceRulesResponse
import com.example.m_commerce.features.orders.data.remote.ShopifyAdminApiService
import com.shopify.buy3.GraphClient
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CouponRemoteDataSourceImplTest {

    private lateinit var dataSource: CouponRemoteDataSourceImpl
    private val mockGraphClient = mockk<GraphClient>(relaxed = true)
    private val mockApiService = mockk<ShopifyAdminApiService>(relaxed = true)

    @Before
    fun setUp() {
        dataSource = CouponRemoteDataSourceImpl(mockGraphClient, mockApiService)
    }

    @Test
    fun `getCoupons should return list of coupons when API is successful`() = runTest {
        val ruleId = 123456L
        val rule = PriceRuleDto(
            id = ruleId,
            title = "Summer Sale",
            value = "-20.0",
            value_type = "percentage",
            allocation_method = "",
            target_type = "",
            starts_at = "",
            ends_at = "",
            usage_limit = 50 ,
            once_per_customer = true,
            created_at = "",
            updated_at = ""
        )

        val code = DiscountCodeDto(
            id = 1,
            code = "SUMMER20",
            usage_count = 10,
            status = "enabled",
            created_at = "",
            updated_at = "",
            starts_at = "",
            ends_at = "",
            price_rule_id = ruleId
        )

        coEvery { mockApiService.getPriceRules(any()) } returns Response.success(
            PriceRulesResponse(priceRules = listOf(rule))
        )

        coEvery { mockApiService.getDiscountCodes(ruleId.toString(), any()) } returns Response.success(
            com.example.m_commerce.features.coupon.domain.entity.DiscountCodesResponse(
                discountCodes = listOf(code)
            )
        )

        val result = dataSource.getCoupons("dummy_token").first()

        val expectedCoupon = Coupon(
            id = 1,
            code = "SUMMER20",
            usageCount = 10,
            status = "enabled",
            summary = "20.0% off entire order"
        )

        assertEquals(listOf(expectedCoupon), result)
    }

    @Test
    fun `getCoupons should throw Exception when price rules API fails`() = runTest {
        // Arrange: mock failed API response
        coEvery { mockApiService.getPriceRules(any()) } returns Response.error(
            500,
            ResponseBody.create(null, "Internal Server Error")
        )

        try {
            // Act: call the method that should throw
            dataSource.getCoupons("dummy_token").first()
            // If no exception thrown, fail the test
            org.junit.Assert.fail("Expected an Exception to be thrown")
        } catch (e: Exception) {
            // Assert: exception message is correct
            assertThat(
                e.message,
                `is`("Failed to fetch price rules: Internal Server Error")
            )
        }
    }



    @Test
    fun `getCoupons should return empty list when price rules is null`() = runTest {
        val priceRules = mutableListOf<PriceRuleDto>(
            PriceRuleDto(
                id = 123456L,
                title = "Summer Sale",
                value = "-20.0",
                value_type = "percentage",
                allocation_method = "",
                target_type = "",
                starts_at = "",
                ends_at = "",
                usage_limit = 50 ,
                once_per_customer = true,
                created_at = "",
                updated_at = ""
            )
        )
        coEvery { mockApiService.getPriceRules(any()) } returns Response.success(
            PriceRulesResponse(priceRules )
        )

        val result = dataSource.getCoupons("dummy_token").first()
        assertTrue(result.isEmpty())
    }
}
