package app.foodin.domain.csQna

import app.foodin.domain.common.BaseDomain
import app.foodin.domain.user.User
import app.foodin.domain.user.UserInfoDto
import com.fasterxml.jackson.annotation.JsonIgnore

data class CsQna(override var id: Long = 0L,
                 var question: String,
                 var answer: String?,
                 @JsonIgnore
                 var writeUser: User) : BaseDomain(id){

    var writeUserInfo = writeUser.let{UserInfoDto(writeUser)}
}
