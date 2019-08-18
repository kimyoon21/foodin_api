package app.foodin.domain.common

import java.io.Serializable
import java.sql.Timestamp

abstract class BaseDomain(
        // id 를 꺼내서 계속 생성자에 넣게 한 이유가 있었는데 뭐였지.
    open var id: Long = 0
) : Serializable {

    var createdTime: Timestamp? = null

    var updatedTime: Timestamp? = null

    fun setDefaultValues(id: Long, createdTime: Timestamp?, updatedTime: Timestamp?) {
        this.id = id
        this.createdTime = createdTime
        this.updatedTime = updatedTime
    }

    open fun setFromRequest(requestDto: Any){}

}
