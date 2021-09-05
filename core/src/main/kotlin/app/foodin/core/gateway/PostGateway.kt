package app.foodin.core.gateway

import app.foodin.domain.recipe.Post
import app.foodin.domain.recipe.PostFilter
import app.foodin.domain.recipe.PostUserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostGateway : StatusGateway<Post, PostFilter> {
    fun findDtoBy(filter: PostFilter, pageable: Pageable): Page<PostUserDto>
    fun addCommentCount(id: Long, count: Int)
    fun addLoveCount(id: Long, count: Int)
}
