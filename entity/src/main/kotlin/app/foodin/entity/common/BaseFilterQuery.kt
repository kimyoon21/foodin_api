package app.foodin.entity.common

import app.foodin.common.extension.hasValueLet
import app.foodin.domain.common.BaseDomain
import org.springframework.data.jpa.domain.Specification
import kotlin.reflect.KProperty1

interface BaseFilterQuery<D : BaseDomain, E : BaseEntity<D>> {

    /****
     * 자유검색 : query 를 이용해 여러 필드에 or 검색
     */
    fun querysToSpecification(vararg specs: Specification<E>?): Specification<E> {
        return or(specs.toList())
    }

    fun toSpecification(): Specification<E>
}

fun <E, R> equalFilter(property: KProperty1<E, R>, value: R?): Specification<E>? = value.hasValueLet {
    property.equal(it)
}

fun <E> likeFilter(property: KProperty1<E, String?>, value: String?, mathMode: MathMode): Specification<E>? = value.hasValueLet {
    when (mathMode) {
        MathMode.ANYWHERE -> property.like("%$value%")
        MathMode.PRE -> property.like("$value%")
        MathMode.POST -> property.like("%$value")
    }
}

fun <E, R : Any> inListFilter(property: KProperty1<E, R?>, values: Collection<R>): Specification<E>? = values.hasValueLet {
    property.`in`(values)
}

fun <E, R : Any> filterIfHasValue(value: R, specification: Specification<E>): Specification<E>? = value.hasValueLet {
   specification
}