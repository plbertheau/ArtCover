package com.plbertheau.data.module

import android.content.Context
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.plbertheau.data.repository.ArtCoverTrackRepository
import com.plbertheau.data.repository.ArtCoverTrackRepositoryImpl
import com.plbertheau.data.service.ArtCoverApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(OkHttpProfilerInterceptor())
        return builder
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideArtCoverApi(okHttpClient: OkHttpClient): ArtCoverApi {
        val api: ArtCoverApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://static.leboncoin.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ArtCoverApi::class.java)
        }
        return api
    }

    @Provides
    @Singleton
    fun provideArtCoverTrackRepository(artCoverApi: ArtCoverApi): ArtCoverTrackRepository {
        return ArtCoverTrackRepositoryImpl(artCoverApi)
    }

}