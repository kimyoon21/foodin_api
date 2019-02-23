package app.foodin.entity.common.search

import javax.validation.constraints.NotEmpty

data class SearchCriteria(
        @NotEmpty
        val key: String,
        @NotEmpty
        val operation: SearchOperation,
        @NotEmpty
        val value: Any
)

