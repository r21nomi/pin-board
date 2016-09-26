package com.r21nomi.pinboard.util

import java.util.*

/**
 * Created by Ryota Niinomi on 2016/09/27.
 */
class StringUtil {
    companion object {
        fun split(str: String, separatorChar: Char): List<String> {
            return splitWorker(str, separatorChar, true)!!
        }

        private fun splitWorker(str: String?, separatorChar: Char, preserveAllTokens: Boolean): List<String>? {
            // Performance tuned for 2.0 (JDK1.4)

            if (str == null) {
                return null
            }
            val len = str.length
            if (len == 0) {
                return ArrayList()
            }
            val list = ArrayList<String>()
            var i = 0
            var start = 0
            var match = false
            var lastMatch = false
            while (i < len) {
                if (str[i] == separatorChar) {
                    if (match || preserveAllTokens) {
                        list.add(str.substring(start, i))
                        match = false
                        lastMatch = true
                    }
                    start = ++i
                    continue
                }
                lastMatch = false
                match = true
                i++
            }
            if (match || preserveAllTokens && lastMatch) {
                list.add(str.substring(start, i))
            }
            return list
        }
    }
}