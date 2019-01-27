package app.foodin.entity.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotEmpty


@Embeddable
@JsonIgnoreProperties("host", "id")
class Email {

    @NotEmpty
    @javax.validation.constraints.Email
    @Column(name = "email", nullable = false, unique = true)
    val value: String

    val host: String
        get() {
            val index = value.indexOf("@")
            return value.substring(index)
        }

    val id: String
        get() {
            val index = value.indexOf("@")
            return value.substring(0, index)
        }

    constructor(value: String) {
        this.value = value
    }
}