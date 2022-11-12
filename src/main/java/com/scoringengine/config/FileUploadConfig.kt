package com.scoringengine.config

import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.commons.CommonsMultipartResolver


internal class FileUploadConfig {
    fun multipartResolver(): MultipartResolver {
        val multipartResolver = CommonsMultipartResolver()
        multipartResolver.setMaxUploadSize(1000)
        multipartResolver.setMaxUploadSizePerFile(1000)
        return multipartResolver
    }
}
