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


    private fun getRootData() {
        //Coroutine
        launch(Dispatchers.Default) {
            try {
                rooted = RootUtilities.isRootAvailableOnDevice()
                rootGivenBool = RootUtilities.isRootGivenForDevice()
                busyBoxInstalledBool = RootUtilities.isBusyBoxInstalled()

                //UI Thread
                withContext(Dispatchers.Main) {
                    if (rooted) {
                        binding.RootTextDesc.text = Constants.DEVICE_ROOTED
                        binding.RootTextDesc.setTextColor(Color.parseColor("#00E676"))

                        binding.DeviceRooted.text = Constants.YES
                        binding.RootPathTextTV.text = RootUtilities.findBinaryLocationPath()

                        if (rootGivenBool)
                            binding.RootGivenDeviceText.text = Constants.TRUE
                        else
                            binding.RootGivenDeviceText.text = Constants.FALSE


                        if (rooted) {
                            binding.RootGivenDeviceText.text = Constants.TRUE
                            binding.DeviceRootAvailableOnDevice.text = Constants.YES
                        } else {
                            binding.DeviceRootAvailableOnDevice.text = Constants.NO
                            binding.RootGivenDeviceText.text = Constants.FALSE
                        }

                        if (busyBoxInstalledBool)
                            binding.busyBoxInstalledOnDevice.text = Constants.YES
                        else
                            binding.busyBoxInstalledOnDevice.text = Constants.NO

                    } else {
                        binding.DeviceRootAvailableOnDevice.text = Constants.NO
                        binding.RootGivenDeviceText.text = Constants.NO
                        binding.RootPathTextTV.text = Constants.SYMBOL_HYPHEN
                        binding.DeviceRooted.text = Constants.NO
                        binding.busyBoxInstalledOnDevice.text = Constants.NO
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //Cancels the coroutine scope including its job
        cancel()
    }
}
