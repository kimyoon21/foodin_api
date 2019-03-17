package app.foodin.domain.writable

import app.foodin.domain.user.User

interface UserWritable {
    var writeUser: User?

    var writeUserId: Long?

    fun getWriterUser() = writeUser

    fun setWriterUser(user: User) {
        writeUser = user
    }

    fun getWriterUserId() = writeUserId

    fun setWriterUserId(userId: Long) {
        writeUserId = userId
    }
}
