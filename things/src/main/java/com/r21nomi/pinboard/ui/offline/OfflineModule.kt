package com.r21nomi.pinboard.ui.offline

import com.r21nomi.pinboard.domain.offline_image.GetOfflineImages
import dagger.Module
import dagger.Provides

/**
 * Created by r21nomi on 2017/08/03.
 */
@Module
class OfflineModule {
    @Provides
    @OfflineScope
    fun provideOfflineViewModel(getOfflineImages: GetOfflineImages): OfflineViewModel {
        return OfflineViewModel(getOfflineImages)
    }
}