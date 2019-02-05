package app.foodin.entity.common

import com.google.common.base.Joiner
import org.springframework.data.jpa.domain.Specification
import java.util.regex.Pattern

class SearchSpec<T>{

    var spec : Specification<T>? = null

    var search : String? = null
    set(value) {
        val builder = EntitySpecificationBuilder<T>()
        val operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET)
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