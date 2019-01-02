package app.foodin.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

/**
 * UserDetails 클래스를 확장하여 UserDetailsServices 에서 바로 사용할 수 있도록
 */
@Entity
class User(
        /**
         * 사용자를 구분하는 유일키. UserDetails 에서 username
         */
        @Column(nullable = false, unique = true, length = 100)
        private var email: String,

        @Column(length = 200)
        private var password: String? = null,

        /**
         * 이름
         */
        @Column(nullable = false, length = 100)
        var name: String,

        /*** for UserDetails */

        private var enabled: Boolean = true,

        private var credentialsExpired: Boolean = false,

        private var accountExpired: Boolean = false,

        private var accountLocked: Boolean = false

        /*** for UserDetails */

) : UserDetails, Serializable {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Version
    private var version: Int = 0

    @field:CreationTimestamp
    private lateinit var createTime: Timestamp

    @field:UpdateTimestamp
    private lateinit var updateTime: Timestamp

    override fun getUsername() = email

    override fun getPassword() = password

    override fun isEnabled() = enabled

    override fun isCredentialsNonExpired() = !credentialsExpired

    override fun isAccountNonExpired() = !accountExpired

    override fun isAccountNonLocked() = !accountLocked

}