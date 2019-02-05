package app.foodin.domain.common

import java.io.Serializable
import java.sql.Timestamp

open class Base : Serializable{
    var id: Long? = null

    var createdTime: Timestamp? = null

    var updatedTime: Timestamp? = null
}