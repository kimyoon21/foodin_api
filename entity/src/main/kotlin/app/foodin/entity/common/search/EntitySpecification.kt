package app.foodin.entity.common.search

import app.foodin.entity.common.search.SearchOperation.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class EntitySpecification<T>(
    private val criteria: SearchCriteria
) : Specification<T> {

    override fun toPredicate(
        root: Root<T>,
        query: CriteriaQuery<*>,
        builder: CriteriaBuilder
    ): Predicate? {

        return when (criteria.operation) {
            EQUALITY -> builder.equal(root.get<Any>(criteria.key), criteria.value)
            NEGATION -> builder.notEqual(root.get<Any>(criteria.key), criteria.value)
            GREATER_THAN -> builder.greaterThan(root.get<String>(
                    criteria.key), criteria.value.toString())
            LESS_THAN -> builder.lessThan(root.get<String>(
                    criteria.key), criteria.value.toString())
            STARTS_WITH -> builder.like(root.get(criteria.key), criteria.value.toString() + "%")
            ENDS_WITH -> builder.like(root.get(criteria.key), "%" + criteria.value)
            CONTAINS -> builder.like(root.get(
                    criteria.key), "%" + criteria.value + "%")
            LIKE -> builder.like(root.get(
                    criteria.key), criteria.value.toString())
            IS_NULL -> builder.isNull(root.get<Any>(criteria.key))
            IS_NOT_NULL -> builder.isNotNull(root.get<Any>(criteria.key))
            else -> null
        }
    }
}
