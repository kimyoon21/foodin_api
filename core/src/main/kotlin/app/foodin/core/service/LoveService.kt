package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NEED
import app.foodin.core.gateway.FoodGateway
import app.foodin.core.gateway.LoveGateway
import app.foodin.core.gateway.ReviewGateway
import app.foodin.domain.love.Love
import app.foodin.domain.love.LoveFilter
import app.foodin.domain.love.LoveReq
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoveService(override val gateway: LoveGateway,
                  val foodGateway: FoodGateway,
                  val reviewGateway: ReviewGateway) : BaseService<Love,
        LoveFilter>() {
    /***
     * 좋아요 부재시 추가, 존재시 삭제
     */
    fun addOrDelete(loveReq: LoveReq): Love? {
        if (loveReq.foodId == null && loveReq.recipeId == null && loveReq.reviewId == null) {
            throw CommonException(EX_NEED, "word.targetId")
        }

        val loveFilter = LoveFilter(loveReq)
        val page = findAll(loveFilter, Pageable.unpaged())
        return if (page.isEmpty) {
            val love = Love(loveReq)
            saveFrom(love)
            addLoveCount(loveReq, 1)
            love

        } else {
            deleteById(page.content[0].id)
            addLoveCount(loveReq, -1)
            null
        }
    }

    private fun addLoveCount(loveReq: LoveReq, i: Int) {
        if (loveReq.foodId != null) {
            foodGateway.addLoveCount(loveReq.foodId!!, i)
        } else if (loveReq.reviewId != null) {
            reviewGateway.addLoveCount(loveReq.reviewId!!, i)
        }
    }
}
