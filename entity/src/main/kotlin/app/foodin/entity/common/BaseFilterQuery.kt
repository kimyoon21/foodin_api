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

fun <E, R> equalFilter(property: KProperty1<E, R>, query: R?): Specification<E>? = query.hasValueLet {
    property.equal(it)
}

fun <E> likeFilter(property: KProperty1<E, String?>, query: String?, mathMode: MathMode): Specification<E>? = query.hasValueLet {
    when (mathMode) {
        MathMode.ANYWHERE -> property.like("%$query%")
        MathMode.PRE -> property.like("$query%")
        MathMode.POST -> property.like("%$query")
    }
}

fun <E, R : Any> inListFilter(property: KProperty1<E, R?>, values: Collection<R>): Specification<E>? = values.hasValueLet {
    property.`in`(values)
}
