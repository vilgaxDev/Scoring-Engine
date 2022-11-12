package com.scoringengine.config

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import java.io.IOException
import java.security.*
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


@Slf4j
@Configuration
open class JwtConfiguration {

    @Value("\${app.security.jwt.keystore-location}")
    private val keyStorePath: String? = null

    @Value("\${app.security.jwt.keystore-password}")
    private val keyStorePassword: String? = null

    @Value("\${app.security.jwt.key-alias}")
    private val keyAlias: String? = null

    @Value("\${app.security.jwt.private-key-passphrase}")
    private val privateKeyPassphrase: String? = null

    @Bean
    open fun keyStore(): KeyStore {
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            val resourceAsStream = Thread.currentThread().contextClassLoader.getResourceAsStream(keyStorePath)
            keyStore.load(resourceAsStream, keyStorePassword!!.toCharArray())
            return keyStore
        } catch (e: IOException) {
            println("Unable to load keystore: {}")//, keyStorePath, e)
        } catch (e: CertificateException) {
            println("Unable to load keystore: {}")//, keyStorePath, e)
        } catch (e: NoSuchAlgorithmException) {
            println("Unable to load keystore: {}")//, keyStorePath, e)
        } catch (e: KeyStoreException) {
            println("Unable to load keystore: {}")//, keyStorePath, e)
        }
        throw IllegalArgumentException("Unable to load keystore")
    }

    @Bean
    open fun jwtSigningKey(keyStore: KeyStore): RSAPrivateKey {
        try {
            val key: Key = keyStore.getKey(keyAlias, privateKeyPassphrase!!.toCharArray())
            if (key is RSAPrivateKey) {
                return key
            }
        } catch (e: UnrecoverableKeyException) {
            println("Unable to load private key from keystore: {}")//, keyStorePath, e)
        } catch (e: NoSuchAlgorithmException) {
            println("Unable to load private key from keystore: {}")//, keyStorePath, e)
        } catch (e: KeyStoreException) {
            println("Unable to load private key from keystore: {}")//, keyStorePath, e)
        }
        throw IllegalArgumentException("Unable to load private key")
    }

    @Bean
    open fun jwtValidationKey(keyStore: KeyStore): RSAPublicKey {
        try {
            val certificate: Certificate = keyStore.getCertificate(keyAlias)
            val publicKey: PublicKey = certificate.getPublicKey()
            if (publicKey is RSAPublicKey) {
                return publicKey
            }
        } catch (e: KeyStoreException) {
            println("Unable to load private key from keystore: {}")//, keyStorePath, e)
        }
        throw IllegalArgumentException("Unable to load RSA public key")
    }

    @Bean
    open fun jwtDecoder(rsaPublicKey: RSAPublicKey?): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build()
    }

}