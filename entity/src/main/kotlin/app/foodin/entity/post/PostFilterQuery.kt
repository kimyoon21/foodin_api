package app.foodin.entity.post

import app.foodin.common.extension.hasValueLet
import app.foodin.domain.recipe.Post
import app.foodin.domain.recipe.PostFilter
import app.foodin.entity.common.*
import org.springframework.data.jpa.domain.Specification

class PostFilterQuery(val filter: PostFilter) : BaseFilterQuery<Post, PostEntity> {
    override fun toSpecification(): Specification<PostEntity> =
            filter.let { filter ->
                and(
                        equalFilter(PostEntity::status, filter.status),
                        filter.query.hasValueLet { q ->
                            querysToSpecification(
                                    likeFilter(PostEntity::name, q, MatchMode.ANYWHERE),
                                    likeFilter(PostEntity::contents, q, MatchMode.ANYWHERE)
                            )
                        },
                        isNotNullFilter(PostEntity::mainImageUri, filter.hasImage)

                )
            }
}
