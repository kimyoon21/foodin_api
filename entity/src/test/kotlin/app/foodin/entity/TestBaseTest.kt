package app.foodin.entity

import app.foodin.entity.common.EntitySpecification
import app.foodin.entity.common.SearchCriteria
import app.foodin.entity.common.SearchOperation
import app.foodin.entity.food.FoodEntity
import app.foodin.entity.food.FoodRepository
import app.foodin.entity.user.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import javax.transaction.Transactional


//@RunWith(SpringRunner::class)
@RunWith(SpringJUnit4ClassRunner::class)
@Transactional
@Rollback
class TestBaseTest : TestBase() {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var environment: Environment

    @Autowired
    private lateinit var foodRepository: FoodRepository

    @Test
    fun `context check`() {
        assertNotNull(applicationContext.getBean(UserRepository::class.java))
        assertNotNull(environment.getProperty("spring.datasource.url"))
    }

    @Test
    fun givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        val spec = EntitySpecification<FoodEntity>(
                SearchCriteria("name", SearchOperation.EQUALITY, "만듀"))
        val spec1 = EntitySpecification<FoodEntity>(
                SearchCriteria("maxPrice", SearchOperation.LESS_THAN, 1500))
        val results = foodRepository.findAll(Specification.where(spec).and(spec1))

        assertEquals(1,results.size)
    }
}