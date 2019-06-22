package app.foodin.domain.writable

import app.foodin.domain.user.User

interface UserWritable {
    var writeUser: User?

    var writeUserId: Long?
}
