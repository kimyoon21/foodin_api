package app.foodin.service

import app.foodin.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Database 를 바탕으로 인증 처리
 * UserRepository 에서 회원 정보를 읽어와서 인증 처리
 * User 는 UserDetails 클래스를 확장했기 때문에 별도 변환 처리 필요 없음
 */
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository
            .findByEmail(username)
            .orElseThrow {
                UsernameNotFoundException("User not found")
            }
}
