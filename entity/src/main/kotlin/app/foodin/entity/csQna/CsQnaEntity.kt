package app.foodin.entity.csQna

import app.foodin.domain.csQna.CsQna
import app.foodin.entity.common.BaseEntity
import app.foodin.entity.user.UserEntity
import javax.persistence.*

@Entity
@Table(name = "cs_qna")
data class CsQnaEntity(val question: String,
                       val answer: String?,
                       @ManyToOne
                       @JoinColumn(name = "write_user_id")
                       val writeUser: UserEntity) : BaseEntity<CsQna>() {

    @Column(name = "write_user_id", insertable = false, updatable = false)
    var writeUserId : Long = 0


    constructor(csQna: CsQna) : this(question = csQna.question,
            answer = csQna.answer,
            writeUser = UserEntity(csQna.writeUser!!)) {
        setBaseFieldsFromDomain(csQna)
    }

    override fun toDomain(): CsQna = CsQna(id,question,answer, writeUser.toDomain()).also {
                setDomainBaseFieldsFromEntity(it)
            }}
