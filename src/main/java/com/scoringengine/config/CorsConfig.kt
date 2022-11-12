package com.scoringengine.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.multipart.support.MultipartFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
open class CorsConfig {

    @Bean
    open fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "PATCH")
                    .allowedHeaders("*")
                    .allowedOrigins("*")
                //.allowedOrigins("http://127.0.0.1","http://localhost","http://credable.test", "https://d1h16r9v3hvjsu.cloudfront.net")
            }
        }
    }



    @Bean(name = ["multipartResolver"])
    open fun multipartResolver(): CommonsMultipartResolver? {
        val multipartResolver = CommonsMultipartResolver()
        multipartResolver.setMaxUploadSize(1000000000)
        return multipartResolver
    }


    @Bean
    open fun multipartFilter(): MultipartFilter? {
        val multipartFilter = MultipartFilter()
        multipartFilter.setMultipartResolverBeanName("multipartResolver")
        return multipartFilter
    }

}


