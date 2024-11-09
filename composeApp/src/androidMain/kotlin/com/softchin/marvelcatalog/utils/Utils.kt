package com.softchin.marvelcatalog.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun md5(string: String): String {
    val md5 = "MD5"
    try {
        // Crea el Hash MD5
        val digest =
            MessageDigest
                .getInstance(md5)
        digest.update(string.toByteArray())
        val messageDigest = digest.digest()

        // Crea un string en Hexadecimal
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}