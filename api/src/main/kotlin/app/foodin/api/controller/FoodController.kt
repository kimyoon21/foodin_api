package app.foodin.api.controller

import app.foodin.common.result.ResponseResult
import app.foodin.domain.food.Food
import app.foodin.domain.user.FoodService
import app.foodin.entity.common.EntitySpecificationsBuilder
import app.foodin.entity.common.SearchOperation
import app.foodin.entity.food.FoodEntity
import com.google.common.base.Joiner
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.regex.Pattern


@RestController
@RequestMapping("/food")
class FoodController(
        private val foodService: FoodService
) {

    @GetMapping
    fun getAll(pageable: Pageable,
               @RequestParam(value = "search") search: String): ResponseResult {

        val builder = EntitySpecificationsBuilder<FoodEntity>()
        val operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET)
        val pattern = Pattern.compile(
                "(\\w+?)($operationSetExper)(\\p{Punct}?)(\\w+?)(\\p{Punct}?);", Pattern.UNICODE_CHARACTER_CLASS)
        val matcher = pattern.matcher("$search;")
        while (matcher.find()) {
            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5))
        }

        val spec = builder.build()
        return ResponseResult(foodService.findAll(spec,pageable))
    }

    @PostMapping(consumes = ["application/json"])
    fun register(@RequestBody food: Food): ResponseResult {
        return ResponseResult(foodService.saveFrom(food))
    }

}