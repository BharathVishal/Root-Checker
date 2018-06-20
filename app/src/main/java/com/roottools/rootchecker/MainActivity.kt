package com.roottools.rootchecker

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.roottools.rootchecker.ConstantsUtilities.Constants
import com.roottools.rootchecker.ConstantsUtilities.RootUtilities
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    private lateinit var rootAvailable: TextView
    private lateinit var rootGiven: TextView
    private lateinit var rootPath: TextView
    private lateinit var deviceRooted: TextView
    private lateinit var headerDescText: TextView
    private lateinit var busyBoxInstalledText: TextView
    private var rooted: Boolean = false
    private var rootGivenBool: Boolean = false
    private var busyBoxInstalledBool: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootPath = findViewById(R.id.Root_Path_text)
        rootGiven = findViewById(R.id.Root_Given_Text)
        rootAvailable = findViewById(R.id.Device_Root_Available)
        deviceRooted = findViewById(R.id.Device_Rooted)
        headerDescText = findViewById(R.id.Root_Text_Desc)
        busyBoxInstalledText = findViewById(R.id.busy_Box_Installed)

        getRootData()
    }


    fun getRootData() {
        doAsync {
            rooted = RootUtilities.isRootAvailable()
            rootGivenBool = RootUtilities.isRootGiven()
            busyBoxInstalledBool = RootUtilities.isBusyBoxInstalled()

            uiThread {
                if (rooted) {
                    headerDescText.text = Constants.DEVICE_ROOTED
                    headerDescText.setTextColor(Color.parseColor("#00E676"))

                    deviceRooted.text = Constants.YES
                    rootPath.text = RootUtilities.findBinaryLocation()

                    if (rootGivenBool)
                        rootGiven.text = Constants.TRUE
                    else
                        rootGiven.text = Constants.FALSE


                    if (rooted) {
                        rootGiven.text = Constants.TRUE
                        rootAvailable.text = Constants.YES
                    } else {
                        rootAvailable.text = Constants.NO

                        rootGiven.text = Constants.FALSE
                    }

                    if (busyBoxInstalledBool)
                        busyBoxInstalledText.text = Constants.YES
                    else
                        busyBoxInstalledText.text = Constants.NO

                } else {
                    rootAvailable.text = Constants.NO
                    rootGiven.text = Constants.NO
                    rootPath.text = Constants.SYMBOL_HYPHEN
                    deviceRooted.text = Constants.NO
                    busyBoxInstalledText.text = Constants.NO
                }
            }
        }
    }

}
