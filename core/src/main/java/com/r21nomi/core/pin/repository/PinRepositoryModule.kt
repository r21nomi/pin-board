package com.r21nomi.core.pin.repository

import com.r21nomi.core.pin.usecase.PinRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by r21nomi on 2017/02/05.
 */
@Module
class PinRepositoryModule {

    @Provides
    @Singleton
    fun providePinApi(retrofit: Retrofit): PinApi {
        return retrofit.create(PinApi::class.java)
    }

    @Provides
    fun providePinRepository(pinRepositoryImpl: PinRepositoryImpl): PinRepository {
        return pinRepositoryImpl
    }

    interface Provider {
        fun pinRepository(): PinRepository
    }
}