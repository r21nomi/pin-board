package com.r21nomi.pinboard.util

import android.net.Uri
import com.r21nomi.pinboard.util.StringUtil
import rx.Observable

/**
 * Created by Ryota Niinomi on 2016/09/12.
 */
class DeepLinkRouter {

    companion object {
        val ACCESS_TOKEN = "access_token"

        fun getAccessToken(uri: Uri): Observable<String> {
            val host = uri.getHost()
            var path = uri.getPath()

            if (path.startsWith("/")) {
                path = path.substring(1)
            }

            val pathParams = StringUtil.split(path, '/')

            if (fromPinterest(host, pathParams)) {
                val accessToken = uri.getQueryParameter(ACCESS_TOKEN)
                return Observable.just(accessToken)
            } else {
                return Observable.just("")
            }
        }

        private fun fromPinterest(host: String, pathParams: List<String>) : Boolean {
            return "" == host && pathParams.size == 0
        }
    }
}