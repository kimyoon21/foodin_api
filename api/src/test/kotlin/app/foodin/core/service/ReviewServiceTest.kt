package app.foodin.core.service

import app.foodin.core.gateway.ReviewGateway
import app.foodin.domain.review.ReviewCreateReq
import app.foodin.domain.review.ReviewReq
import app.foodin.entity.food.FoodEntity
import app.foodin.entity.food.FoodRepository
import app.foodin.entity.review.ReviewRepository
import app.foodin.entity.user.UserEntity
import app.foodin.entity.user.UserRepository
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
@ActiveProfiles("test")
@Transactional
@Ignore
class ReviewServiceTest {
    @Autowired
    private lateinit var reviewService: ReviewService

    @Autowired
    private lateinit var foodRepository: FoodRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @Autowired
    private lateinit var reviewGateway: ReviewGateway

    private lateinit var user: UserEntity
    private lateinit var food: FoodEntity

    private lateinit var reviewCreateReq: ReviewCreateReq

    @PersistenceContext
    private lateinit var em: EntityManager

    @Before
    fun init() {
        food = foodRepository.findAll().first()
        user = userRepository.findAll().first()

        reviewCreateReq = ReviewCreateReq(
            foodId = food.id, reviewReq = ReviewReq(price = 3000, rating = 3.5f, tagList = mutableListOf("매움")),
            writeUserId = 99
        )
    }

    @After
    fun after() {
    }

    @Test
    fun save() {
        reviewService.save(reviewCreateReq)
    }

    @Test
    fun update() {
        val saved = reviewService.save(reviewCreateReq)
        val review = reviewGateway.findById(saved.id)!!
        reviewService.update(review.id, reviewCreateReq.reviewReq.copy(rating = 5.0f))

        em.flush()
        with(reviewGateway.findById(saved.id)!!) {
            assert(this.rating == 5.0f)
        }
    }
}