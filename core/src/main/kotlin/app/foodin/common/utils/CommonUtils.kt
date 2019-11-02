package app.foodin.common.utils

import org.hibernate.Hibernate
import org.hibernate.collection.spi.PersistentCollection
import org.hibernate.proxy.HibernateProxy
import java.util.*

fun getCleanUUID(): String {
    return UUID.randomUUID().toString().replace("-", "")
}

fun isProxyObjectInit(obj: Any?): Boolean {
    return when (obj) {
        null -> false
        is PersistentCollection -> obj.wasInitialized()
        is HibernateProxy -> Hibernate.isInitialized(obj)
        else -> true
    }
}
