package app.foodin.entity.common

import app.foodin.common.extension.hasValueLet
import app.foodin.common.utils.getAuthenticatedUserInfo
import app.foodin.domain.common.BaseDomain
import kotlin.reflect.KProperty1
import org.springframework.data.jpa.domain.Specification

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

fun <E> isNullFilter(property: KProperty1<E, *>, value: Boolean?): Specification<E>? = value.hasValueLet {
    property.isNull()
}

fun <E> isNotNullFilter(property: KProperty1<E, *>, value: Boolean?): Specification<E>? = value.hasValueLet {
    property.isNotNull()
}

fun <E> likeFilter(property: KProperty1<E, String?>, value: String?, matchMode: MatchMode): Specification<E>? = value.hasValueLet {
    when (matchMode) {
        MatchMode.ANYWHERE -> property.like("%$value%")
        MatchMode.PRE -> property.like("$value%")
        MatchMode.POST -> property.like("%$value")
    }
}

fun <E> tagFilter(property: KProperty1<E, String?>, tag: String?): Specification<E>? = tag.hasValueLet {
        property.like("%$tag %")
}

fun <E, R : Any> inListFilter(property: KProperty1<E, R?>, values: Collection<R>): Specification<E>? = values.hasValueLet {
    property.`in`(values)
}

fun <E, R : Any> filterIfHasValue(value: R?, specification: Specification<E>): Specification<E>? = value.hasValueLet {
   specification
}

fun <E> equalUserIdFilterWhenUserPrivateDomain(property: KProperty1<E, Long?>): Specification<E>? {
    if (getAuthenticatedUserInfo().hasOnlyUserRole()) {
        return equalFilter(property, getAuthenticatedUserInfo().id)
    }
    return null
}
