package app.foodin.api.controller

import app.foodin.core.service.ImageUploadService
import app.foodin.domain.ImageInfo
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/image")
class ImageUploadController {

    private val logger = LoggerFactory.getLogger(ImageUploadController::class.java)

    @RequestMapping(method = [RequestMethod.POST])
    fun upload(
            imageCategory: ImageUploadService.ImageCategory,
            multipartFiles: Array<MultipartFile>
            ) : ResponseEntity<ImageInfo> {



        return ResponseEntity.ok("admin")
    }
}