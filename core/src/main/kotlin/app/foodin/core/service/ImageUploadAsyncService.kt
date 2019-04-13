package app.foodin.core.service

import app.foodin.common.exception.CommonException
import app.foodin.common.exception.EX_CANNOT
import app.foodin.common.extension.hasValue
import app.foodin.common.utils.getCleanUUID
import app.foodin.domain.ImageInfo
import app.foodin.domain.common.EntityType
import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.metadata.MetadataException
import com.drew.metadata.bmp.BmpHeaderDirectory
import com.drew.metadata.gif.GifHeaderDirectory
import com.drew.metadata.jpeg.JpegDirectory
import com.drew.metadata.png.PngDirectory
import com.drew.metadata.webp.WebpDirectory
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection
import java.util.concurrent.Future
import javax.annotation.PostConstruct

@Service
class ImageUploadAsyncService {

    private val logger = LoggerFactory.getLogger(ImageUploadAsyncService::class.java)
    private val maxWidth = 1200
    private val maxFileSize = 1024 * 1024 * 6 // 6MB
    private val maxRetry = 3

    private val clientRegion = Regions.AP_NORTHEAST_2
    private val bucketName = "app.foodin.images"
    private val accessKey = "AKIAITKXECDSZWUMZZBA"
    private val secretKey = "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"
    private val cfDomain = "https://image.foodin.app"

    private lateinit var s3Client: AmazonS3

    @PostConstruct
    fun initS3Client() {
        try {
            logger.info("s3client connection init ---- ")

            val awsCreds = BasicAWSCredentials(accessKey, secretKey)
            s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(AWSStaticCredentialsProvider(awsCreds))
                    .withRegion(clientRegion)
                    .build()
        } catch (ex: Exception) {
            logger.error("s3 client init error", ex)
        }
    }

    fun makeFilePath(imageCategory: EntityType, ext: String): String {
        val imgFolder = imageCategory.name.toLowerCase()
        return "$imgFolder/${System.currentTimeMillis()}_${getCleanUUID()}.$ext"
    }

    fun s3Upload(filePath: String, inputStream: InputStream, metadata: ObjectMetadata) {
        logger.info("============ start upload to s3")
        var retryNo = 0
        // 재시도프로세스.
        while (retryNo < maxRetry) {
            try {
                metadata.contentType = URLConnection.guessContentTypeFromName(filePath)
                // Upload a text string as a new object.
                s3Client.putObject(bucketName, filePath, inputStream, metadata)
                return
            } catch (e: AmazonServiceException) {
                // The call was transmitted successfully, but Amazon S3 couldn't process
                // it, so it returned an error response.
                logger.error(e.errorMessage, e)
                // S3 재접속.
                retryNo++
            } catch (e: SdkClientException) {
                // Amazon S3 couldn't be contacted for a response, or the client
                // couldn't parse the response from Amazon S3.
                logger.error(e.message, e)
                retryNo++
            }
        }
        throw CommonException(EX_CANNOT)
    }

    @Async
    fun deleteImage(uri: String?) {
        if (uri == null) {
            return
        }
        // 버킷 호스트 제거

        val bucketUrlRegx = "https://.*.app/"
        val filePath = uri.replaceFirst(bucketUrlRegx.toRegex(), "")

        logger.info("base uri : $filePath")
        // 기본 이미지 삭제
        s3Client.deleteObject(DeleteObjectRequest(bucketName, filePath))
        logger.info("delete completed. $bucketName/$filePath")
//        TODO()
        var targetUrl: String? = null
        // size별 이미지 삭제.
//        for (postFix in IMG_TYPE_POST_FIXS) {
//            targetUrl = uri.replace(".", postFix + ".")
//            logger.info("targetUrl : $targetUrl")
//            s3Client.deleteObject(DeleteObjectRequest(awsBucketName, targetUrl))
//        }
    }

    /*******
     * 파일 사이즈가 너무 크거나, 가로길이 등이 넘칠때만 리사이징한 뒤
     * s3 업로드 하고 정보 내려주기
     */
    @Async
    fun uploadImage(type: EntityType, inputStream: InputStream): Future<ImageInfo> {
        val outputStream = ByteArrayOutputStream()
        try {
            logger.info("============ start upload a image")
            var imageLength = 0
            val buff = ByteArray(1024 * 8)

            var byteRead = 0
            while (inputStream.read(buff).also { byteRead = it } != -1) {
                outputStream.write(buff, 0, byteRead)
                imageLength += byteRead
            }

            if (imageLength > maxFileSize) {
                throw CommonException(EX_CANNOT, "act.upload_big_file")
            }

            val uploadStream = ByteArrayInputStream(outputStream.toByteArray())

            val imageInfo = readImageInformation(uploadStream)

            // 혹시 1200 이상의 넓이의 이미지라면 1200으로 줄여야 한다 TODO
            if (imageInfo.width == null || imageInfo.width!! > maxWidth) {
                // TODO 이미지 크기 줄이기
            }

            uploadStream.reset()
            // 메타데이터 생성.
            val metadata = ObjectMetadata()
            metadata.contentLength = imageLength.toLong()
            val uploadPath = makeFilePath(type, imageInfo.ext!!)

            s3Upload(uploadPath, uploadStream, metadata)

            logger.info("============ finish upload a image")
            imageInfo.let {
                it.uri = "$cfDomain/$uploadPath"
                it.sizeKb = imageLength / 1024
                it.type = type
                return AsyncResult(it)
            }
        } catch (e: Exception) {
            // close
            inputStream.close()
            outputStream.close()
            throw e
        } finally {
            // close
            inputStream.close()
            outputStream.close()
        }
    }

    @Throws(IOException::class, MetadataException::class, ImageProcessingException::class, CommonException::class)
    fun readImageInformation(imageStream: InputStream): ImageInfo {
        var width = 0
        var height = 0
        var ext: String? = null
        val metadata = ImageMetadataReader.readMetadata(imageStream)
        for (directory in metadata.directories) {
            for (tag in directory.tags) {
                logger.info("tag : $tag")
            }

            if (directory.hasErrors()) {
                for (error in directory.errors) {
                    logger.error("ERROR: $error")
                }
            }

            when (directory) {
                is JpegDirectory -> {
                    width = directory.imageWidth
                    height = directory.imageHeight
                    ext = "jpg"
                }
                is PngDirectory -> {
                    width = directory.getInt(PngDirectory.TAG_IMAGE_WIDTH)
                    height = directory.getInt(PngDirectory.TAG_IMAGE_HEIGHT)
                    ext = "png"
                }
                is WebpDirectory -> {
                    width = directory.getInt(WebpDirectory.TAG_IMAGE_WIDTH)
                    height = directory.getInt(WebpDirectory.TAG_IMAGE_HEIGHT)
                    ext = "webp"
                }
                is GifHeaderDirectory -> {
                    width = directory.getInt(GifHeaderDirectory.TAG_IMAGE_WIDTH)
                    height = directory.getInt(GifHeaderDirectory.TAG_IMAGE_HEIGHT)
                    ext = "gif"
                }
                is BmpHeaderDirectory -> {
                    width = directory.getInt(BmpHeaderDirectory.TAG_IMAGE_WIDTH)
                    height = directory.getInt(BmpHeaderDirectory.TAG_IMAGE_HEIGHT)
                    ext = "bmp"
                }
                else -> {
                }
            }
            if (ext.hasValue()) {
                break
            }
        }
//        val directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory::class.java)
//        var orientation = 1
//        try {
//            if (directory != null)
//                orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION)
//        } catch (me: MetadataException) {
//            logger.warn("Could not get orientation. $orientation")
//            orientation = 1
//        }

        return ImageInfo(width = width, height = height, ext = ext)
    }
}