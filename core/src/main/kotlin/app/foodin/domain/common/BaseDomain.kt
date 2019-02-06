package app.foodin.domain.common

import java.io.Serializable
import java.sql.Timestamp

open class BaseDomain(
        open var id: Long?
) : Serializable{

    var createdTime: Timestamp? = null

    var updatedTime: Timestamp? = null

}

