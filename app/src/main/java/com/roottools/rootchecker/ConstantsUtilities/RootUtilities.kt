/**
 *
 * Copyright 2018-2024 Bharath Vishal G.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **/

@file:Suppress("unused")

package com.roottools.rootchecker.ConstantsUtilities

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*


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


    fun findBinaryLocationPath(): String {
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


    fun isRootAvailableOnDevice(): Boolean {
        for (pathDir in System.getenv("PATH")?.split(":".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()!!) {
            if (File(pathDir, "su").exists()) {
                return true
            }
        }
        return false
    }


    fun isBusyBoxInstalled(): Boolean {
        return checkBinaryBusyBoxInstalled("busybox")
    }


    private fun checkBinaryBusyBoxInstalled(filename: String): Boolean {
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


    fun isRootGivenForDevice(): Boolean {
        if (isRootAvailableOnDevice()) {
            var process: Process? = null
            try {
                process = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
                val input = BufferedReader(InputStreamReader(process!!.inputStream))
                val output = input.readLine()
                if (output != null && output.lowercase(Locale.getDefault()).contains("uid=0"))
                    return true
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                process?.destroy()
            }
        }
        return false
    }


    val isOSSandAbove: Boolean
        get() {
            val sdkInt = Build.VERSION.SDK_INT
            var releaseInt = 0
            try {
                val releaseStr = Build.VERSION.RELEASE
                releaseInt = releaseStr.toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return sdkInt >= 31
        }


}