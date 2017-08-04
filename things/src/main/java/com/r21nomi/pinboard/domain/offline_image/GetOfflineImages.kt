package com.r21nomi.pinboard.domain.offline_image

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.support.annotation.DrawableRes
import rx.Single
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/08/03.
 */
class GetOfflineImages @Inject constructor(private val context: Context) {

    fun execute(count: Int): Single<List<Uri>> {
        val list = (0..(count - 1)).map { i ->
            val id = getId("img_" + i.toString())
            createUri(id)
        }
        return Single.just(list)
    }

    private fun getId(name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }

    private fun createUri(@DrawableRes resId: Int): Uri {
        val resources = context.resources
        return Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(resId))
                .appendPath(resources.getResourceTypeName(resId))
                .appendPath(resources.getResourceEntryName(resId))
                .build()
    }
}