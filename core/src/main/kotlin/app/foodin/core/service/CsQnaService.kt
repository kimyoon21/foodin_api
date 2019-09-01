package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_ACCESS_DENIED
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.core.gateway.CsQnaGateway
import app.foodin.domain.csQna.CsQna
import app.foodin.domain.csQna.CsQnaCreateReq
import app.foodin.domain.csQna.CsQnaFilter
import app.foodin.domain.csQna.CsQnaUpdateReq
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CsQnaService(override val gateway: CsQnaGateway) : BaseService<CsQna, CsQnaFilter>() {

    fun create(createReq: CsQnaCreateReq): CsQna {
        val qna = CsQna(question = createReq.question, writeUser = getAuthenticatedUserInfo().toUser(), answer = null)
        return gateway.saveFrom(qna)
    }

    fun update(id: Long, updateReq: CsQnaUpdateReq): CsQna {
        val oldQna = findById(id)
        oldQna.answer = updateReq.answer
        return gateway.saveFrom(oldQna)
    }

    fun delete(id: Long): Boolean {
        return findById(id).let {
            if (getAuthenticatedUserInfo().id != it.writeUser.id) {
                throw CommonException(EX_ACCESS_DENIED)
            }
            deleteById(id)
            true
        }
    }
}
