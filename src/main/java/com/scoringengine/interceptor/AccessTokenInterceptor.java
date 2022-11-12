package com.scoringengine.interceptor;
/*
import okhttp3.Interceptor;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import java.io.IOException;
import java.util.*;


public class AccessTokenInterceptor implements Interceptor{

@Value("${mpesa.stk.consumer.key}")
public String consumerKey = "xNmQeszm0BcksrsBIMhfsQ3rCYrCUbuk";//"7iLthlg5IJyc3K1LHhJDhQrU8jpyVWYD"

@Value("${mpesa.stk.consumer.secret}")
public String consumerSecret = "gnEaldAQiYUZwISu";//"gxWY6ezAizA5CRp9"
        }

@Throws(IOException::class)
    override fun intercept(
@NonNull chain: Interceptor.Chain,

        ): Response {

        val keys = "$consumerKey:$consumerSecret"
        val encodedString: String = Base64.getEncoder().encodeToString(keys.toByteArray())
        val request = chain.request().newBuilder()
        .addHeader("Authorization", "Basic $encodedString")
        .addHeader("cache-control", "no-cache")
        .build()
        return chain.proceed(request)
        }
        }
*/