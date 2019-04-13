package app.foodin.entity.common

import app.foodin.common.enums.Status
import app.foodin.domain.common.StatusDomain
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class StatusEntity<D : StatusDomain> : BaseEntity<D>(){
    lateinit var status: Status

}
