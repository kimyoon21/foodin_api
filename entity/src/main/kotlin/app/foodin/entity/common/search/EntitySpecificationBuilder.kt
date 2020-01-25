package app.foodin.entity.common.search

import app.foodin.entity.common.search.SearchOperation.*
import org.springframework.data.jpa.domain.Specification

class EntitySpecificationBuilder<T> {

    private val params: MutableList<SearchCriteria> = mutableListOf()

    fun with(key: String, operation: String, value: Any, prefix: String, suffix: String): EntitySpecificationBuilder<T> {

        var op = SearchOperation.getSimpleOperation(operation[0])
        if (op != null) {
            if (op === LIKE) {
                val startWithAsterisk = prefix.contains("*")
                val endWithAsterisk = suffix.contains("*")

                if (startWithAsterisk && endWithAsterisk) {
                    op = CONTAINS
                } else if (startWithAsterisk) {
                    op = ENDS_WITH
                } else if (endWithAsterisk) {
                    op = STARTS_WITH
                }
            } else if (op === NULL_CHECK) {
                op = if (value == "1") {
                    IS_NOT_NULL
                } else {
                    IS_NULL
                }
            }
            params.add(SearchCriteria(key, op, value))
        }
        return this
    }

    fun build(): Specification<T>? {
        if (params.isEmpty()) {
            return null
        }

        var result: Specification<T> = EntitySpecification(params[0])

        for (i in 1 until params.size) {
            result = Specification.where(result)?.and(EntitySpecification(params[i]))!!
        }

        return result
    }
}
