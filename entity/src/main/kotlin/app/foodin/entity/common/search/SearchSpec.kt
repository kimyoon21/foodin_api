package app.foodin.entity.common.search

import java.util.regex.Pattern
import org.springframework.data.jpa.domain.Specification

class SearchSpec<T> {

    var spec: Specification<T>? = null

    var search: String? = null
    set(value) {
        val builder = EntitySpecificationBuilder<T>()
        val operationSetExper = SearchOperation.SIMPLE_OPERATION_SET.joinToString("|")
        val pattern = Pattern.compile(
                "(\\w+?)($operationSetExper)(\\p{Punct}?)(\\w+?)(\\p{Punct}?);", Pattern.UNICODE_CHARACTER_CLASS)
        val matcher = pattern.matcher("$value;")
        while (matcher.find()) {
            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5))
        }

        spec = builder.build()
    }
}
