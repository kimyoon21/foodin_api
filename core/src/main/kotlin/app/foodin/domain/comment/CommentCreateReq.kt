package app.foodin.domain.comment

import app.foodin.core.annotation.KotlinNoArgConstructor
import java.io.Serializable

/***
 * 댓글 등록 수정용
 */
@KotlinNoArgConstructor
data class CommentCreateReq(
    val parentId: Long,
    val writeUserId: Long,
    var commentReq: CommentReq
) : Serializable

@KotlinNoArgConstructor
data class CommentUpdateReq(
    var commentReq: CommentReq
) : Serializable

@KotlinNoArgConstructor
data class CommentReq(
    val contents: String? = null,
    val imageUriList: MutableList<String> = mutableListOf()
)