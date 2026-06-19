package com.sathish.tandem.di

import com.sathish.tandem.BuildConfig
import com.sathish.tandem.api.CommunityApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    // Define your network-related dependencies here
    single(named("BaseUrl")) {
        provideRetrofit(get())
    }

    single { okHttpClient() }
    single<CommunityApiService> {
        get<Retrofit>(named("BaseUrl")).create(CommunityApiService::class.java)
    }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

private fun okHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().apply {
        connectTimeout(60L, TimeUnit.SECONDS)
        writeTimeout(20L, TimeUnit.SECONDS)
        readTimeout(20L, TimeUnit.SECONDS)
        protocols(listOf(Protocol.HTTP_1_1))
        addInterceptor(provideLoggingInterceptor())
    }.build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor  = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}
