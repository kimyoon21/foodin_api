package app.foodin.api.controller

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_NEED
import app.foodin.common.extension.throwNullOrEmpty
import app.foodin.common.result.ResponseResult
import app.foodin.core.service.ImageUploadService
import app.foodin.domain.ImageInfo
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/image")
class ImageUploadController (
        private val imageUploadService: ImageUploadService
){

    private val logger = LoggerFactory.getLogger(ImageUploadController::class.java)

    @PostMapping(consumes = ["multipart/form-data"])
    fun upload(
            @RequestParam(required = false) imageCategory: ImageUploadService.ImageCategory,
            images: List<MultipartFile?>
    ): ResponseResult {
        images.throwNullOrEmpty { throw CommonException(EX_NEED) }
        return ResponseResult(imageUploadService.uploadImages(imageCategory, images))

    }

    @DeleteMapping(consumes = ["multipart/form-data"])
    fun delete(
            @RequestParam(required = false) imageUris: List<String>
    ): ResponseResult {
        imageUris.throwNullOrEmpty { throw CommonException(EX_NEED) }
        val imageInfos = imageUris.map { ImageInfo(uri = it) }
        return ResponseResult(imageUploadService.deleteImages(imageInfos))

    }
}