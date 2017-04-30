package com.r21nomi.pinboard.ui.login

import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinboard.domain.login.SaveAccessToken
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/02/05.
 */
@Module
class MainModule(val activity: AppCompatActivity) {

    @Provides
    @MainScope
    fun provideActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    @MainScope
    fun provideMainViewModel(getPins: GetPins, saveAccessToken: SaveAccessToken): MainViewModel {
        return MainViewModel(getPins, saveAccessToken)
    }
}