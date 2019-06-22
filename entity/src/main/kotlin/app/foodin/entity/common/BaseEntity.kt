package app.foodin.entity.common

import app.foodin.domain.common.BaseDomain
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity<D : BaseDomain> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @field:CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdTime: Timestamp? = null

    @field:UpdateTimestamp
    @Column(nullable = false)
    var updatedTime: Timestamp? = null

    abstract fun toDomain(): D

    fun setBaseFields(baseDomain:D){
        this.id = baseDomain.id
        this.createdTime = baseDomain.createdTime
        this.updatedTime = baseDomain.updatedTime
    }
}
