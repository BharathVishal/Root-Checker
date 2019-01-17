package com.roottools.rootchecker

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roottools.rootchecker.ConstantsUtilities.Constants
import com.roottools.rootchecker.ConstantsUtilities.RootUtilities
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    private var rooted: Boolean = false
    private var rootGivenBool: Boolean = false
    private var busyBoxInstalledBool: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Run the async task
        getRootData()
    }


    private fun getRootData() {
        doAsync {
            rooted = RootUtilities.isRootAvailable()
            rootGivenBool = RootUtilities.isRootGiven()
            busyBoxInstalledBool = RootUtilities.isBusyBoxInstalled()

            uiThread {
                if (rooted) {
                    Root_Text_Desc.text = Constants.DEVICE_ROOTED
                    Root_Text_Desc.setTextColor(Color.parseColor("#00E676"))

                    Device_Rooted.text = Constants.YES
                    Root_Path_text.text = RootUtilities.findBinaryLocation()

                    if (rootGivenBool)
                        Root_Given_Text.text = Constants.TRUE
                    else
                        Root_Given_Text.text = Constants.FALSE


                    if (rooted) {
                        Root_Given_Text.text = Constants.TRUE
                        Device_Root_Available.text = Constants.YES
                    } else {
                        Device_Root_Available.text = Constants.NO
                        Root_Given_Text.text = Constants.FALSE
                    }

                    if (busyBoxInstalledBool)
                        busy_Box_Installed.text = Constants.YES
                    else
                        busy_Box_Installed.text = Constants.NO

                } else {
                    Device_Root_Available.text = Constants.NO
                    Root_Given_Text.text = Constants.NO
                    Root_Path_text.text = Constants.SYMBOL_HYPHEN
                    Device_Rooted.text = Constants.NO
                    busy_Box_Installed.text = Constants.NO
                }
            }
        }
    }
}
