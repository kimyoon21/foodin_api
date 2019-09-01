package app.foodin.domain.notice

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_INVALID_REQUEST
import app.foodin.common.extension.listToTags
import app.foodin.common.extension.tagsToList
import app.foodin.domain.common.BaseDomain
import com.fasterxml.jackson.annotation.JsonIgnore

data class Notice(
    override var id: Long = 0L,
    var title: String,
    var contents: String,
    var type: NoticeType,
    @JsonIgnore
    var categoryTags: String?
) : BaseDomain(id) {

    var categoryTagList = categoryTags.tagsToList()

    override fun setFromRequest(requestDto: Any) {
        if (requestDto is Notice) {
            this.title = requestDto.title
            this.contents = requestDto.contents
            this.type = requestDto.type
            this.categoryTags = requestDto.categoryTagList.listToTags()
        } else {
            throw CommonException(EX_INVALID_REQUEST)
        }
    }
}

