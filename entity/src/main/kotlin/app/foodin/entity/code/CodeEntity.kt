package app.foodin.entity.code

import app.foodin.domain.code.Code
import app.foodin.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "code")
data class CodeEntity(
    var code: String,
    var codeName: String,
    var codeType: String

) : BaseEntity<Code>() {
    var imageUri: String? = null
    var infoJson: String? = null

    constructor(code: Code) : this(code.code,
            code.codeName,
            code.codeType) {
        this.imageUri = code.imageUri
        this.infoJson = code.infoJson
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
        }
    }
}
