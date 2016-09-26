package com.r21nomi.pinboard.util

/**
 * Created by Ryota Niinomi on 2016/09/26.
 */
class ApiUtil {
    companion object {
        fun getUrlWithQueryParams(baseUrl: String, param: List<Pair<String, String>>): String {
            var url: String = baseUrl

            var i: Int = 0
            for (pair in param) {
                if (i == 0) {
                    url += "?"
                } else {
                    url += "&"
                }
                url += (pair.first + "=" + pair.second)
                i++
            }

            return url
        }
    }
}