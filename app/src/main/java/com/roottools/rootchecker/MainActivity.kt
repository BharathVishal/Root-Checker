package com.roottools.rootchecker

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roottools.rootchecker.ConstantsUtilities.Constants
import com.roottools.rootchecker.ConstantsUtilities.RootUtilities
import com.roottools.rootchecker.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var rooted: Boolean = false
    private var rootGivenBool: Boolean = false
    private var busyBoxInstalledBool: Boolean = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Run the async task
        getRootData()
    }


    fun getRootData() {
        //Coroutine
        launch(Dispatchers.Default) {
            try {
                rooted = RootUtilities.isRootAvailable()
                rootGivenBool = RootUtilities.isRootGiven()
                busyBoxInstalledBool = RootUtilities.isBusyBoxInstalled()

                //UI Thread
                withContext(Dispatchers.Main) {
                    if (rooted) {
                        binding.RootTextDesc.text = Constants.DEVICE_ROOTED
                        binding.RootTextDesc.setTextColor(Color.parseColor("#00E676"))

                        binding.DeviceRooted.text = Constants.YES
                        binding.RootPathText.text = RootUtilities.findBinaryLocation()

                        if (rootGivenBool)
                            binding.RootGivenText.text = Constants.TRUE
                        else
                            binding.RootGivenText.text = Constants.FALSE


                        if (rooted) {
                            binding.RootGivenText.text = Constants.TRUE
                            binding.DeviceRootAvailable.text = Constants.YES
                        } else {
                            binding.DeviceRootAvailable.text = Constants.NO
                            binding.RootGivenText.text = Constants.FALSE
                        }

                        if (busyBoxInstalledBool)
                            binding.busyBoxInstalled.text = Constants.YES
                        else
                            binding.busyBoxInstalled.text = Constants.NO

                    } else {
                        binding.DeviceRootAvailable.text = Constants.NO
                        binding.RootGivenText.text = Constants.NO
                        binding.RootPathText.text = Constants.SYMBOL_HYPHEN
                        binding.DeviceRooted.text = Constants.NO
                        binding.busyBoxInstalled.text = Constants.NO
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}
