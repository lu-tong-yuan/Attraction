package com.tommy.attractions.di
import com.tommy.attractions.AcceptHeaderInterceptor
import com.tommy.attractions.features.main.data.api.AttractionsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val acceptHeaderInterceptor = AcceptHeaderInterceptor()
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 连接超时时间设置为30秒
            .readTimeout(30, TimeUnit.SECONDS) // 读取超时时间设置为30秒
            .addInterceptor(loggingInterceptor)
            .addInterceptor(acceptHeaderInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/") // 替換為實際的 API 地址
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAttractionsApiService(retrofit: Retrofit): AttractionsApiService {
        return retrofit.create(AttractionsApiService::class.java)
    }

}