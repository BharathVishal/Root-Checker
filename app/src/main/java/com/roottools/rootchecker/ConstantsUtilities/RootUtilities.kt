package com.roottools.rootchecker.ConstantsUtilities

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


object RootUtilities {

    private fun isRooted(): Boolean {
        return findBinary()
    }


    private fun findBinary(): Boolean {
        var found = false
        if (!found) {
            val places = arrayOf("/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/")
            for (where in places) {
                if (File(where + "su").exists()) {
                    found = true
                    break
                }
            }
        }
        return found
    }


    fun findBinaryLocation(): String {
        var path = Constants.SYMBOL_HYPHEN
        var found = false
        if (!found) {
            val places = arrayOf("/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/")
            for (where in places) {
                if (File(where + "su").exists()) {
                    path = where + "su"
                    found = true
                    break
                }
            }
        }
        return path
    }


    fun isRootAvailable(): Boolean {
        for (pathDir in System.getenv("PATH").split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()) {
            if (File(pathDir, "su").exists()) {
                return true
            }
        }
        return false
    }


    fun isBusyBoxInstalled(): Boolean {
        return checkForBinaryBusyBox("busybox")
    }


    private fun checkForBinaryBusyBox(filename: String): Boolean {

        val pathsArray = arrayOf("/data/local/", "/data/local/bin/", "/data/local/xbin/", "/sbin/", "/su/bin/", "/system/bin/", "/system/bin/.ext/", "/system/bin/failsafe/", "/system/sd/xbin/", "/system/usr/we-need-root/", "/system/xbin/")
        var result = false
        for (path in pathsArray) {
            val completePath = path + filename
            val f = File(completePath)
            val fileExists = f.exists()
            if (fileExists) {
                result = true
            }
        }
        return result
    }


    fun isRootGiven(): Boolean {
        if (isRootAvailable()) {
            var process: Process? = null
            try {
                process = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
                val `in` = BufferedReader(InputStreamReader(process!!.inputStream))
                val output = `in`.readLine()
                if (output != null && output.toLowerCase().contains("uid=0"))
                    return true
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (process != null)
                    process.destroy()
            }
        }
        return false
    }

}