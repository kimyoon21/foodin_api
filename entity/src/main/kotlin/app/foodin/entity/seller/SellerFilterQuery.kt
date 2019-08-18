package app.foodin.entity.seller

import app.foodin.domain.seller.Seller
import app.foodin.domain.seller.SellerFilter
import app.foodin.entity.common.BaseFilterQuery
import app.foodin.entity.common.MatchMode
import app.foodin.entity.common.and
import app.foodin.entity.common.likeFilter
import org.springframework.data.jpa.domain.Specification

class SellerFilterQuery(val filter: SellerFilter) : BaseFilterQuery<Seller, SellerEntity> {
    override fun toSpecification(): Specification<SellerEntity> = filter.let { filter ->
        and(
                likeFilter(SellerEntity::name, filter.name, MatchMode.PRE)

        )
    }
}
