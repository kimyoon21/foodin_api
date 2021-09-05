package app.foodin.domain.recipe

import app.foodin.core.annotation.KotlinNoArgConstructor
import java.io.Serializable

@KotlinNoArgConstructor
data class PostCreateReq(
    val writeUserId: Long,
    var postReq: PostReq
) : Serializable

@KotlinNoArgConstructor
data class PostUpdateReq(
    var postReq: PostReq
) : Serializable

@KotlinNoArgConstructor
data class PostReq(
    val name: String,
    val foodIdList: MutableList<Long> = mutableListOf(),
    val contents: String,
    val tagList: MutableList<String> = mutableListOf(),
    val mainImageUri: String,
    val imageUriList: MutableList<String> = mutableListOf()
)
