package com.r21nomi.pinboard.ui.main

import com.r21nomi.pinboard.domain.pin.GetPins
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/02/05.
 */
@Module
class MainModule {
    @Provides
    @MainScope
    fun provideMainViewModel(getPins: GetPins): MainViewModel {
        return MainViewModel(getPins)
    }
}