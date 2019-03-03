package app.foodin.entity.common

import app.foodin.domain.common.BaseDomain
import org.springframework.data.jpa.domain.Specification
// no use
// 상속받아 쓰게 하려했지만 귀찮게 상속받는거에 비해 제공해줄 수 있는 부분이 없음
interface BaseFilter<D:BaseDomain,E:BaseEntity<D>>{

    fun toSpecification(): Specification<E>
}