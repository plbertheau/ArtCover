package com.plbertheau.data.module

import android.content.Context
import androidx.room.Room
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.plbertheau.data.Constants.BASE_URL
import com.plbertheau.data.Constants.DATABASE_NAME
import com.plbertheau.data.local.TrackLocalDB
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
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ArtCoverApi::class.java)
        }
        return api
    }

    @Provides
    @Singleton
    fun provideArtCoverTrackRepository(artCoverApi: ArtCoverApi, trackLocalDB: TrackLocalDB): com.plbertheau.domain.repository.ArtCoverTrackRepository {
        return ArtCoverTrackRepositoryImpl(api = artCoverApi, trackDao = trackLocalDB.getTrackDao())
    }

    @Provides
    @Singleton
    fun provideTrackLocalDB(@ApplicationContext context: Context): TrackLocalDB {
        return Room.databaseBuilder(
            context,
            TrackLocalDB::class.java,
            DATABASE_NAME,
        ).build()
    }
}