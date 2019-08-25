package app.foodin.entity.code

import app.foodin.domain.code.Code
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "codes")
data class CodeEntity(
    var code: String,
    var codeName: String,
    var codeType: String

) : BaseEntity<Code>() {
    var imageUri: String? = null
    var infoJson: String? = null
    var seq: Int = Integer.MAX_VALUE

    constructor(code: Code) : this(code.code,
            code.codeName,
            code.codeType) {
        this.imageUri = code.imageUri
        this.infoJson = code.infoJson
        this.seq = code.seq
    }

    override fun toDomain(): Code {
        return Code(code = this.code,
                codeName = this.codeName,
                codeType = this.codeType).also {
            it.id = this.id
            it.createdTime = this.createdTime
            it.updatedTime = this.updatedTime
            it.imageUri = this.imageUri
            it.infoJson = this.infoJson
            it.seq = this.seq
        }
    }
}
