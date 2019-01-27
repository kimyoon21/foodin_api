package app.foodin.api.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin")
class AdminController{

    private val logger = LoggerFactory.getLogger(AdminController::class.java)

    @RequestMapping(method = [RequestMethod.GET])
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun productAll(): ResponseEntity<String> {

        SecurityContextHolder.getContext().authentication

        return ResponseEntity.ok("admin")
    }

}