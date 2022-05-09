package com.match.statistics.di

import com.match.statistics.BuildConfig
import com.match.statistics.data.source.remote.service.LoLService
import com.match.statistics.util.InternalSSLSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.conscrypt.Conscrypt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Security
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {


        Security.insertProviderAt(Conscrypt.newProvider(), 1)

        val tm: X509TrustManager = Conscrypt.getDefaultX509TrustManager()
        val sslContext = SSLContext.getInstance("TLS", "Conscrypt")
        sslContext.init(null, arrayOf(tm), null)

        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .build()

        val client = OkHttpClient.Builder()
            .sslSocketFactory(InternalSSLSocketFactory(sslContext.socketFactory), tm)
            .connectionSpecs(Collections.singletonList(spec))
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(Level.BODY)
            client.addInterceptor(logging)
        }

        return client.build()
    }

//    @Provides
//    @Singleton
//    @Named("LoL")
//    fun provideLolConverterFactory(): GsonConverterFactory {
//        return GsonConverterFactory.create(GsonBuilder()
//            .registerTypeAdapter(
//                Summoner::class.java, SummonerDeserializer())
//            .create())
//    }

    @Singleton
    @Provides
    @Named("LoL")
    fun provideLoLRetrofit(
        okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(LoLService.BASE_URL_V1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideLolService(@Named("LoL") retrofit: Retrofit): LoLService {
        return retrofit.create(LoLService::class.java)
    }
}