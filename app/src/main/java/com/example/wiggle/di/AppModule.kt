package com.example.wiggle.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import coil.ImageLoader
import coil.decode.Decoder
import coil.decode.SvgDecoder
import com.example.wiggle.core.domain.use_case.GetOwnUserIdUseCase
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.DefaultPostLiker
import com.example.wiggle.core.util.PostLiker
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPref(app: Application) : SharedPreferences {
       return app.getSharedPreferences(
           Constants.SHARED_PREF_NAME,
           MODE_PRIVATE
       )
    }

    @Provides
    @Singleton
fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor {
            val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "")
            val modifiedRequest = it.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            it.proceed(modifiedRequest)
        }
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .crossfade(true)
            .componentRegistry{
                add(SvgDecoder(app))
            }
            .build()
    }

    @Provides
    @Singleton
    fun providePostLiker(): PostLiker {
        return DefaultPostLiker()
    }

    @Provides
    @Singleton
    fun provideGetOwnUserIdUseCase(sharedPreferences: SharedPreferences): GetOwnUserIdUseCase {
        return GetOwnUserIdUseCase(sharedPreferences)
    }




}