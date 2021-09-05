package app.foodin.entity.post

import app.foodin.core.gateway.PostGateway
import app.foodin.domain.recipe.Post
import app.foodin.domain.recipe.PostFilter
import app.foodin.domain.recipe.PostUserDto
import app.foodin.entity.common.BaseRepositoryInterface
import app.foodin.entity.common.StatusRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : BaseRepositoryInterface<PostEntity> {

    @Modifying
    @Query("UPDATE PostEntity set loveCount = loveCount + :count where id = :id")
    fun addLoveCount(@Param("id") id: Long, @Param("count") count: Int)

    @Modifying
    @Query("UPDATE PostEntity set commentCount = commentCount + :count where id = :id")
    fun addCommentCount(@Param("id") id: Long, @Param("count") count: Int)
}

@Component
class JpaPostRepository(private val repository: PostRepository) : StatusRepository<Post,
        PostEntity, PostFilter>(repository), PostGateway {
    override fun saveFrom(t: Post): Post {
        return repository.saveAndFlush(PostEntity(t)).toDomain()
    }

    override fun findDtoBy(filter: PostFilter, pageable: Pageable): Page<PostUserDto> {
        return repository.findAll(PostFilterQuery(filter).toSpecification(), pageable).map { x -> x.toDto() }
    }

    override fun addLoveCount(id: Long, count: Int) {
        repository.addLoveCount(id, count)
    }

    override fun addCommentCount(id: Long, count: Int) {
        repository.addCommentCount(id, count)
    }

    override fun findAllByFilter(filter: PostFilter, pageable: Pageable): Page<Post> {
        return findAll(PostFilterQuery(filter), pageable)
    }
}
