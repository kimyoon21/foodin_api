package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NEED
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.gateway.LoveGateway
import app.foodin.domain.foodLove.Love
import app.foodin.domain.foodLove.LoveFilter
import app.foodin.domain.foodLove.LoveReq
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoveService(override val gateway: LoveGateway) : BaseService<Love,
        LoveFilter>() {
    /***
     * 좋아요 부재시 추가, 존재시 삭제
     */
    fun addOrDelete(loveReq: LoveReq): Love? {
        if (loveReq.foodId == null && loveReq.recipeId == null && loveReq.reviewId == null) {
            throw CommonException(EX_NEED,"word.targetId")
        }
        loveReq.userId = getAuthenticatedUserInfo().id

        val loveFilter = LoveFilter(loveReq)
        val page = findAll(loveFilter, Pageable.unpaged())
        return if(page.isEmpty) {
            val love = Love(loveReq)
            saveFrom(love)
        }
        else {
            deleteById(page.content[0].id)
            null
        }

    }
}
