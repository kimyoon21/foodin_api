package app.foodin.servlet

import org.apache.commons.io.IOUtils
import java.io.*
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class MultiReadableHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private var cachedBytes: ByteArrayOutputStream? = null

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        if (cachedBytes == null)
            cacheInputStream()

        return CachedServletInputStream()
    }

    @Throws(IOException::class)
    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream))
    }

    @Throws(IOException::class)
    private fun cacheInputStream() {
        cachedBytes = ByteArrayOutputStream()
        IOUtils.copy(super.getInputStream(), cachedBytes)
    }

    inner class CachedServletInputStream : ServletInputStream() {
        private val input: ByteArrayInputStream

        init {
            input = ByteArrayInputStream(cachedBytes!!.toByteArray())
        }

        override fun isFinished(): Boolean {
            return input.available() == 0
        }

        override fun isReady(): Boolean {
            return true
        }

        override fun setReadListener(readListener: ReadListener) {
            throw RuntimeException("Not implemented")
        }

        override fun read(): Int {
            return input.read()
        }
    }
}