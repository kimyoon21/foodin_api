package app.foodin.domain.recipe

import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User
import app.foodin.domain.writable.UserWritable

data class Recipe(
        override var id: Long = 0L
) : BaseDomain(id), UserWritable {
    override var writeUser: User? = null
    override var writeUserId: Long? = null
}
