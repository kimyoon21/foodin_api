package app.foodin.core.service

import app.foodin.entity.food.FoodRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ActiveProfiles("test")
class FoodServiceTest {

    @Autowired
    private lateinit var foodService: FoodService

    @Autowired
    private lateinit var foodRepository: FoodRepository

    @Test
    fun addReviewAndRatingCount() {

        val foodEntity = foodRepository.findAll().first()
        val old = foodEntity.copy()
        foodService.addReviewAndRatingInfo(foodEntity.id, true, 1)
        foodRepository.flush()

        assertEquals(old.reviewCount + 1, foodEntity.reviewCount)
        assertEquals(old.ratingCount + 1, foodEntity.ratingCount)
    }
}
