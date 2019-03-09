package app.foodin.entity.food

import app.foodin.domain.food.FoodFilter
import app.foodin.entity.common.*
import app.foodin.entity.food.FoodFilterQuery.Companion.hasNameLike
import app.foodin.entity.food.FoodFilterQuery.Companion.hasSellerNameIn
import app.foodin.entity.food.FoodFilterQuery.Companion.hasTagLike
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [FoodTest::class], properties = ["classpath:entity.properties"])
@SpringBootApplication
@Transactional
class FoodTest {

    @Autowired
    lateinit var foodRepo: FoodRepository

    lateinit var bebero: FoodEntity
    lateinit var melona: FoodEntity
    lateinit var haribo: FoodEntity

    @Before
    fun setup() {
        with(foodRepo) {
            bebero = save(
                    FoodEntity(
                            name = "빼빼로",
                            categoryId = 5).also {
                        it.companyName = "롯데"
                        it.sellerNames = "CU GS25"
                        it.rating = 1.3F
                        it.tags = "초코 메론"
                    })

            melona = save(
                    FoodEntity(
                            name = "메로나",
                            categoryId = 3).also {
                        it.companyName = "빙그레"
                        it.sellerNames = "CU GS25 7ELEVEN"
                        it.rating = 3.0F
                        it.minPrice = 2000
                        it.maxPrice = 3000
                        it.tags = "메론"
                    })

            haribo = save(
                    FoodEntity(
                            name = "하리보",
                            categoryId = 1).also {
                        it.companyName = "Haribo"
                        it.sellerNames = "CU"
                        it.rating = 5.0F
                        it.minPrice = 5000
                        it.maxPrice = 5000
                        it.tags = "젤리"
                    })
        }
    }

    @After
    fun tearDown() {
        foodRepo.deleteAll()
    }

    /**
     * A collection of FoodEntityQueries is equivalent to an OR of all the queries in the collection.
     */
    fun Iterable<FoodFilterQuery>.toSpecification(): Specification<FoodEntity> = or(
            map { filter -> filter.toSpecification() }
    )

    @Test
    fun `Get a food by id`() {
        val show = foodRepo.findById(bebero.id).get()
        assertThat(show, equalTo(bebero))
    }

    @Test
    fun `Get a food by id equality`() {
        val show = foodRepo.findOne(FoodEntity::id.equal(melona.id)).get()
        assertThat(show, equalTo(melona))
    }

    @Test
    fun `Get foods by id notEqual`() {
        val foods = foodRepo.findAll(FoodEntity::name.notEqual(melona.name))
        assertThat(foods, containsInAnyOrder(haribo, bebero))
    }

    @Test
    fun `Get food by id in`() {
        val foods = foodRepo.findAll(FoodEntity::id.`in`(setOf(bebero.id, melona.id)))
        assertThat(foods, containsInAnyOrder(bebero, melona))
    }

    @Test
    fun `Get food by id lt`() {
        val foods = foodRepo.findAll(FoodEntity::id.lt(haribo.id))
        assertThat(foods, containsInAnyOrder(bebero, melona))
    }

    @Test
    fun `Get food by name between`() {
        val foods = foodRepo.findAll(FoodEntity::minPrice.between(1000, 4000))
        assertThat(foods, containsInAnyOrder(melona))
    }
//
//    @Test
//    fun `Get food by ratings isEmpty`() {
//        val foods = foodRepo.findAll(FoodEntity::starRatings.isEmpty())
//        assertThat(foods, containsInAnyOrder(bebero))
//    }
//
//    @Test
//    fun `Get food by ratings isNotEmpty`() {
//        val foods = foodRepo.findAll(FoodEntity::starRatings.isNotEmpty())
//        assertThat(foods, containsInAnyOrder(haribo, melona))
//    }
//
//    @Test
//    fun `Get food by isMember`() {
//        val foods = foodRepo.findAll(FoodEntity::starRatings.isMember(melona.starRatings.first()))
//        assertThat(foods, containsInAnyOrder(melona))
//    }
//
//    @Test
//    fun `Get food by isNotMember`() {
//        val foods = foodRepo.findAll(FoodEntity::starRatings.isNotMember(haribo.starRatings.first()))
//        assertThat(foods, containsInAnyOrder(melona, bebero))
//    }

    @Test
    fun `Get a food by name like`() {
        val foods = foodRepo.findAll(FoodEntity::name.like("메%"))
        assertThat(foods, containsInAnyOrder(melona))
    }

    @Test
    fun `Get a food by name notLike`() {
        val foods = foodRepo.findAll(FoodEntity::name.notLike("%로%"))
        assertThat(foods, not(containsInAnyOrder(bebero)))
    }

//    @Test
//    fun `Get a food by synopsis notLike with escape char`() {
//        val foods = foodRepo.findAll(FoodEntity::summary.notLike("%\\.", escapeChar = '\\'))
//        assertThat(foods, containsInAnyOrder(bebero))
//    }

    @Test
    fun `Find foodentity by query DTO`() {
        val query = FoodFilterQuery(FoodFilter(categoryIdList = listOf(haribo.categoryId, melona.categoryId)))
        val foods = foodRepo.findAll(query.toSpecification())
        assertThat(foods, containsInAnyOrder(haribo, melona))
    }

    @Test
    fun `Find foods by query DTO - empty query`() {
        val query = FoodFilterQuery(FoodFilter())
        val foods = foodRepo.findAll(query.toSpecification())
        assertThat(foods, containsInAnyOrder(haribo, bebero, melona))
    }

    @Test
    fun `Find foods by multiple query DTOs`() {
        val queries = listOf(
                FoodFilterQuery(FoodFilter(name = bebero.name)),
                FoodFilterQuery(FoodFilter(tag = haribo.tags))
        )
        val foods = foodRepo.findAll(queries.toSpecification())
        assertThat(foods, containsInAnyOrder(haribo, bebero))
    }

    @Test
    fun `Find foods by empty query DTOs list`() {
        val queries = listOf<FoodFilterQuery>()
        val foods = foodRepo.findAll(queries.toSpecification())
        assertThat(foods, containsInAnyOrder(haribo, bebero, melona))
    }

    @Test
    fun `Find foods by inlined query`() {
        val foods = foodRepo.findAll(and(
                hasSellerNameIn(listOf("CU")),
                hasTagLike("메론")), Pageable.unpaged()
        )

        assertThat(foods, allOf(iterableWithSize(2), hasItem(melona)))
    }

    @Test
    fun `Find foods by complex inlined query`() {
        val foods = foodRepo.findAll(
                or(
                        and(
                                hasSellerNameIn(listOf("CU")),
                                likeFilter(FoodEntity::tags,"메론",MathMode.ANYWHERE)
                        ),
                        and(
                                hasNameLike(haribo.name)
                        )
                ), Pageable.unpaged()
        )
        assertThat(foods, containsInAnyOrder(bebero, haribo, melona))
    }
}