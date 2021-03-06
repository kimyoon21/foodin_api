package app.foodin.entity.comment

import app.foodin.domain.common.StatusDomain
import app.foodin.entity.common.StatusEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@MappedSuperclass
abstract class BaseCommentEntity<D : StatusDomain, PD : StatusDomain, P : StatusEntity<PD>>(
    open var parentId: Long,
    @Column(name = "write_user_id")
    open var writeUserId: Long
) : StatusEntity<D>() {

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var parent: P

    var loveCount: Int = 0

    @ManyToOne
    @JoinColumn(name = "write_user_id", insertable = false, updatable = false)
    lateinit var writeUserEntity: UserEntity

    var contents: String? = null

    @Column(columnDefinition = "TEXT")
    var imageUris: String? = null
}
