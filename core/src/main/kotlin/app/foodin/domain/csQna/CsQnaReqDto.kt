package app.foodin.domain.csQna

import app.foodin.core.annotation.KotlinNoArgConstructor
import java.io.Serializable

@KotlinNoArgConstructor
data class CsQnaCreateReq(
        val question: String,
        val categoryTagList : List<String>
) : Serializable

@KotlinNoArgConstructor
data class CsQnaUpdateReq(

        var answer: String
) : Serializable