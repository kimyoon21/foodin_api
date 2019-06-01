package app.foodin.domain.recipe

import app.foodin.domain.common.StatusDomain
import app.foodin.domain.user.User
import app.foodin.domain.writable.UserWritable

data class Recipe(
    override var id: Long = 0L
) : StatusDomain(id), UserWritable {
    override var writeUser: User? = null
    override var writeUserId: Long? = null
}
