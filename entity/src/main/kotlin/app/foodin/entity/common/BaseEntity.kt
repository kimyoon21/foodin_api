package app.foodin.entity.common

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @field:CreationTimestamp
    @Column(nullable = false, updatable = false)
    lateinit var createdTime: Timestamp

    @field:UpdateTimestamp
    @Column(nullable = false)
    lateinit var updatedTime: Timestamp


    abstract fun toDomain(): T
}

