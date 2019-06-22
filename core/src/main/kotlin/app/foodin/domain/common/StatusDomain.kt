package app.foodin.domain.common

import app.foodin.common.enums.Status
import java.io.Serializable
import java.sql.Timestamp

// status 를 지녀서 삭제돼도 db 에 남아있는 데이터
open class StatusDomain(
    override var id: Long = 0,
    var status: Status = Status.APPROVED
) : BaseDomain(id), Serializable {

    fun setDefaultValues(id: Long, createdTime: Timestamp?, updatedTime: Timestamp?, status: Status) {
        setDefaultValues(id, createdTime, updatedTime)
        this.status = status
    }
}
