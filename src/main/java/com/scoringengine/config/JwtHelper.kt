package com.scoringengine.config

import com.scoringengine.dto.Constants
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


@Slf4j
@Component
public class JwtHelper() {


    fun createJwtForClaims(subject: String, role: String, claims: MutableMap<String, Any>): String {
        val id: String = UUID.randomUUID().toString()
        val jwt = doGenerateToken(id, Constants.JWT_ISSUER_ID, subject, role, claims)
        return jwt
    }

    fun decodeJWT(jwt: String): Claims? {
        try {
            var claim = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(Constants.JWT_SECRET_KEY))
                .parseClaimsJws(jwt).body
            return claim
        } catch (e: Exception) {
            // check if exception is due to ExpiredJwtException
            if (e.message!!.contains("io.jsonwebtoken.ExpiredJwtException")) {
                println("Expired jwt token exception, pending review")
                //Refresh Token
                //refreshToken();
                //try again with refresh token
                //response = getData();
            }
            e.printStackTrace()
            return null
        }
    }

    fun doGenerateToken(
        id: String,
        issuer: String,
        subject: String,
        role: String,
        claims: MutableMap<String, Any>
    ): String {
        val signatureAlgorithm = SignatureAlgorithm.HS256
        val apiKeySecretBytes: ByteArray = DatatypeConverter.parseBase64Binary(Constants.JWT_SECRET_KEY)
        val signingKey: Key = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)

        val issuedAt: LocalDateTime = LocalDateTime.now()
        val expiration: LocalDateTime = issuedAt.plusYears(1)
        val issueDate = Date.from(issuedAt.atZone(ZoneId.of( "Africa/Nairobi" )).toInstant())
        val expiryDate = Date.from(expiration.atZone(ZoneId.of( "Africa/Nairobi" )).toInstant())
        println("Issued at: $issuedAt".uppercase())
        println("Expires at: $expiration".uppercase())

        //Let's set the JWT Claims
        val builder: JwtBuilder = Jwts.builder().setId(id)
            .setIssuedAt(issueDate)
            .setAudience(role)
            .setClaims(claims)
            .setSubject(subject)
            .setIssuer(issuer)
            .signWith(signatureAlgorithm, signingKey)


        builder.setIssuedAt(issueDate)
        builder.setExpiration(expiryDate)
        builder.setAudience(role)
        builder.setSubject(subject)
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact()
    }

    fun doGenerateRefreshToken(token: String): String {
        val claim = decodeJWT(token)
        if (claim != null) {
            val signatureAlgorithm = SignatureAlgorithm.HS256
            val apiKeySecretBytes: ByteArray = DatatypeConverter.parseBase64Binary(Constants.JWT_SECRET_KEY)
            val signingKey: Key = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)
            val issuedAt: LocalDateTime = LocalDateTime.now()
            val expiration: LocalDateTime = issuedAt.plusYears(1)
            val issueDate = Date.from(issuedAt.atZone(ZoneId.of( "Africa/Nairobi" )).toInstant())
            val expiryDate = Date.from(expiration.atZone(ZoneId.of( "Africa/Nairobi" )).toInstant())
            val issueDtNow = Date.from(Instant.now())
            val diffInMillies: Long = Math.abs(issueDtNow.getTime() - issueDate.getTime())
            val diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)
            if (diff > 3) {
                return "ANYWAIR TOKEN EXPIRED $diff HOURS AGO, PLEASE TRY LOGIN AGAIN!"
            }
            if (claim.issuer != Constants.JWT_ISSUER_ID) {
                return "INVALID TOKEN ISSUED BY - ${claim.issuer} FLAGGED, PLEASE TRY LOGIN AGAIN!"
            }
            claim.expiration = expiryDate
            claim.issuedAt = issueDate
            val subject = claim.subject
            val role = claim.audience
            val issuer = claim.issuer
            val id = claim.id
            val builder: JwtBuilder = Jwts.builder().setId(id)
                .setIssuedAt(issueDate)
                .setClaims(claim)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey)

            builder.setIssuedAt(issueDate)
            builder.setExpiration(expiryDate)
            builder.setAudience(role)
            builder.setSubject(subject)
            return builder.compact()
        } else {
            return "ANYWAIR INVALID ACCESS TOKEN"
        }
    }


}

