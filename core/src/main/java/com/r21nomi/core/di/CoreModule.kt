package com.r21nomi.core.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.firebase.database.FirebaseDatabase
import com.r21nomi.core.login.repository.LoginRepositoryModule
import com.r21nomi.core.pin.repository.PinRepositoryModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by r21nomi on 2017/03/12.
 */
@Module(
        includes = arrayOf(
                LoginRepositoryModule::class,
                PinRepositoryModule::class
        )
)
class CoreModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(StethoInterceptor())  // FIXME: Do not set if its debug build.
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://api.pinterest.com")
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    /**
     * Define only classes which are used in another module.
     * These classes can be used in another module.
     */
    interface Provider :
            LoginRepositoryModule.Provider,
            PinRepositoryModule.Provider {
    }
}