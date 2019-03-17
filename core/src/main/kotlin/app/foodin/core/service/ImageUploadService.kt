package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_FAILED
import app.foodin.common.exception.EX_NOT_EXISTS
import app.foodin.domain.ImageInfo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.util.concurrent.Future

@Service
class ImageUploadService(
    val imageUploadAsyncService: ImageUploadAsyncService
) {

    enum class ImageCategory {
        FOOD,
        REVIEW,
        RECIPE,
        PROFILE,
        BADGE,
        ETC
    }

    private val logger = LoggerFactory.getLogger(ImageUploadService::class.java)
    private val DETAIL_WIDTH = 720
    private val MAX_SIZE = 1024 * 1024 * 2
    private val MAX_FILE_SIZE = 1024 * 1024 * 6 // 6MB

    fun uploadImages(category: ImageCategory, uploadImages: List<MultipartFile?>): List<ImageInfo?> {
        val imageInfoList = ArrayList<ImageInfo?>(uploadImages.size)
        val asyncResultList = ArrayList<Future<ImageInfo>>()
        var success = true
        logger.info(" ########## upload images 전체 시작! ###########")
        val startTime = System.currentTimeMillis()
        var cnt = 0
        for (image in uploadImages) {
            cnt++
            if (image == null) {
                throw CommonException(EX_FAILED)
            }
            // *** check image is valid
            logger.info("up : " + image.originalFilename + "(" + image.size + ") " + image)
            if (image.size <= 0) {
                logger.info("image is empty. skip upload")
                throw CommonException(EX_FAILED)
            }
            logger.info(" ########## upload image 시작! ###########$cnt")
            val m1 = System.currentTimeMillis()
            logger.info(" 타임 : " + (m1 - startTime))
            val result: Future<ImageInfo>
            try {
                result = imageUploadAsyncService.uploadImage(category, image.inputStream)
                asyncResultList.add(result)
                // 미리 파일수만큼 null 을 채워둔 후, 후에 매칭해서 넣어주기
                imageInfoList.add(null)
            } catch (e: Exception) {
                logger.error("Image async uploading  ERROR ", e)
                throw CommonException(e.localizedMessage)
            }
        }
        try {
            // async 다 돌리고 난 뒤 메소드 콜백 웨이팅.
            for ((idx, future) in asyncResultList.withIndex()) {
                logger.info(" ########## 전체이미지 비동기로 던진뒤 대기 ###########$cnt")
                val imageInfo: ImageInfo = future.get() ?: throw CommonException(EX_NOT_EXISTS)

                imageInfo.let {
                    // 정상 작동.
                    logger.info("정상업로드 된 이미지 리스트에 추가 : ${imageInfo.uri}")
                    // 순서에 맞게 이미지 url 넣어준다
                    imageInfoList.set(idx, imageInfo)
                }
            }
            logger.info(" ########## upload images 전체완료! ###########$cnt")
            val end = System.currentTimeMillis()
            logger.info(" 타임 : " + (end - startTime))
        } catch (e: Exception) {
            logger.error(" Image async upload callback ERROR ", e)
            deleteImages(imageInfoList)
            throw CommonException(e.message!!)
        }

        return imageInfoList
    }

    fun deleteImages(uris: List<ImageInfo?>) {
        for (uri in uris) {
            deleteImage(uri!!)
        }
    }

    fun deleteImage(imageInfo: ImageInfo) {
        imageUploadAsyncService.deleteImage(imageInfo.uri)
    }
}