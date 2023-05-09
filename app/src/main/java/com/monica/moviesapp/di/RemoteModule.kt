package com.monica.moviesapp.di

import com.monica.moviesapp.BuildConfig
import com.monica.moviesapp.data.helpers.ApplicationDispatchersProvider
import com.monica.moviesapp.data.helpers.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier


@Qualifier
annotation class BaseUrl

@Qualifier
annotation class OkHttpLogger

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @BaseUrl
    @Provides
    internal fun provideBaseUrl(): String {
        return "https://api.themoviedb.org/3/"
    }

    @Provides
    @OkHttpLogger
    internal fun provideOkHttpClientLogger(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    internal fun provideOkHttpClient(
        @OkHttpLogger logger: Interceptor,
    ): OkHttpClient {
        val client =
            OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(logger)
        }
        return client.build()
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return ApplicationDispatchersProvider()
    }

    @Provides
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}