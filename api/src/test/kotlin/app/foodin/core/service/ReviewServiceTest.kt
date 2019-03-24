package app.foodin.core.service

import app.foodin.domain.review.ReviewCreateReq
import app.foodin.entity.food.FoodEntity
import app.foodin.entity.food.FoodRepository
import app.foodin.entity.user.UserEntity
import app.foodin.entity.user.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ActiveProfiles("test")
class ReviewServiceTest {
    @Autowired
    private lateinit var reviewService: ReviewService

    @Autowired
    private lateinit var foodRepository: FoodRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var user: UserEntity
    private lateinit var food: FoodEntity
    @Before
    fun init() {
        food = foodRepository.findAll().first()
        user = userRepository.findAll().first()
    }

    @Test
    fun save() {
        val reviewReq = ReviewCreateReq(
            foodId = food.id, price = 3000, rating = 3.5f, tagList = mutableListOf("매움"),
            writeUserId = user.id
        )
        reviewService.saveFrom(reviewReq)
    }
}