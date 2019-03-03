package app.foodin.entity.user

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Birth {

    @Column(name = "birth_full_day")
    val birthFullDay: LocalDate?
    @Column(name = "birth_year")
    val birthYear: Int?

    val birthMonth: Int?
        get() {
            return birthFullDay?.monthValue
        }

    val birthDay: Int?
        get() {
            return birthFullDay?.dayOfMonth
        }

    constructor(birthFullDay: LocalDate?) {
        this.birthFullDay = birthFullDay
        this.birthYear = birthFullDay?.year
    }
}