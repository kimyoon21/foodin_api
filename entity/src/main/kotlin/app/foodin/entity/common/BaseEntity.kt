package app.foodin.entity.common

import app.foodin.domain.common.BaseDomain
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity<D : BaseDomain> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @field:CreationTimestamp
    @Column(nullable = false, updatable = false)
    open var createdTime: Timestamp = Timestamp(System.currentTimeMillis())

    @field:UpdateTimestamp
    @Column(nullable = false)
    open var updatedTime: Timestamp = Timestamp(System.currentTimeMillis())

    abstract fun toDomain(): D
}
