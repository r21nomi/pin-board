package com.r21nomi.pinboard.ui.pin_detail.di

import android.content.Context
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.ui.login.PinDetailScope
import com.r21nomi.pinboard.ui.pin_detail.PinDetailViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/03/26.
 */
@Module
class PinDetailModule {
    @Provides
    @PinDetailScope
    fun provideMainViewModel(context: Context, getPins: GetPins): PinDetailViewModel {
        return PinDetailViewModel(context, getPins)
    }
}