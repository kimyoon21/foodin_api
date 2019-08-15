package app.foodin.core.gateway

import app.foodin.domain.tag.Tag
import app.foodin.domain.tag.TagFilter

interface TagGateway : BaseGateway<Tag, TagFilter>
