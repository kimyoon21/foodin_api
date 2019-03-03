package app.foodin.domain.common

import java.io.Serializable
import java.sql.Timestamp

open class BaseDomain(
        // id 를 꺼내서 계속 생성자에 넣게 한 이유가 있었는데 뭐였지.
    open var id: Long?
) : Serializable {

    var createdTime: Timestamp? = null

    var updatedTime: Timestamp? = null
}
