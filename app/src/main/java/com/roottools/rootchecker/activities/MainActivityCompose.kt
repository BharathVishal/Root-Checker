package com.roottools.rootchecker.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.color.DynamicColors
import com.roottools.rootchecker.ConstantsUtilities.Constants
import com.roottools.rootchecker.ConstantsUtilities.RootUtilities
import com.roottools.rootchecker.R
import com.roottools.rootchecker.ui.theme.Material3AppTheme
import kotlinx.coroutines.*

class MainActivityCompose : ComponentActivity(), CoroutineScope by MainScope() {
    private lateinit var activityContext: Context

    private var rooted: Boolean = false
    private var rootGivenBool: Boolean = false
    private var busyBoxInstalledBool: Boolean = false

    private var rootedHeaderTextVal = mutableStateOf("Your device is not Rooted")
    private val colInt = android.graphics.Color.parseColor("#AD1457")
    private var rootedHeaderTextColorVal = mutableStateOf(colInt)

    private var rootedTextVal = mutableStateOf("No")
    private var rootPathTextVal = mutableStateOf("No")
    private var rootGivenTextVal = mutableStateOf("No")
    private var rootAvailableOnDeviceVal = mutableStateOf("No")
    private var busyBoxInstalledVal = mutableStateOf("No")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Applies Material dynamic theming
        try {
            DynamicColors.applyToActivityIfAvailable(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        activityContext = this

        setContent {
            Material3AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainViewImplementation()
                }
            }
        }

        getRootData()
    }

    //Kotlin Coroutine to get root data
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
                        rootedHeaderTextVal.value = Constants.DEVICE_ROOTED
                        rootedHeaderTextColorVal.value =
                            android.graphics.Color.parseColor("#00E676")

                        rootedTextVal.value = Constants.YES
                        rootPathTextVal.value = RootUtilities.findBinaryLocationPath()

                        if (rootGivenBool)
                            rootGivenTextVal.value = Constants.TRUE
                        else
                            rootGivenTextVal.value = Constants.FALSE


                        if (rooted) {
                            rootGivenTextVal.value = Constants.TRUE
                            rootAvailableOnDeviceVal.value = Constants.YES
                        } else {
                            rootAvailableOnDeviceVal.value = Constants.NO
                            rootGivenTextVal.value = Constants.FALSE
                        }

                        if (busyBoxInstalledBool)
                            busyBoxInstalledVal.value = Constants.YES
                        else
                            busyBoxInstalledVal.value = Constants.NO

                    } else {
                        rootAvailableOnDeviceVal.value = Constants.NO
                        rootGivenTextVal.value = Constants.NO
                        rootPathTextVal.value = Constants.SYMBOL_HYPHEN
                        rootedTextVal.value = Constants.NO
                        busyBoxInstalledVal.value = Constants.NO
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainViewImplementation() {
        Column {
            TopAppBarMain()
            CardViewMain()
        }
    }

    //Top App bar composable function
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarMain() {
        SmallTopAppBar(
            title = { Text("Root Checker  - Compose Activity") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                containerColor = MaterialTheme.colorScheme.surface
            )
        )
    }

    //CardView composable function
    @Suppress("UNNECESSARY_SAFE_CALL")
    @Composable
    fun CardViewMain() {
        Column {
            Spacer(modifier = Modifier.padding(top = 6.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
                    //Root Image logo composable function
                    ImageLogo()
                    TextHeader()

                    TextHeaderInfo(rootedHeaderTextVal.value, rootedHeaderTextColorVal.value)
                    RowComponentInCard("Device Rooted", rootedTextVal.value)
                    Divider(thickness = 0.5.dp)
                    RowComponentInCard("Root Available", rootAvailableOnDeviceVal.value)
                    Divider(thickness = 0.5.dp)
                    RowComponentInCard("Root Path", rootPathTextVal.value)
                    Divider(thickness = 0.5.dp)
                    RowComponentInCard("Root Given", rootGivenTextVal.value)
                    Divider(thickness = 0.5.dp)
                    RowComponentInCard("Busy Box Installed", busyBoxInstalledVal.value)

                }//end of column
            }//end of card
        }//end of outer column
    }//end of card view main


    //Root Image Logo composable function
    @Composable
    fun ImageLogo() {
        Image(
            painter = painterResource(R.drawable.root_act),
            contentDescription = "Image Logo",
            modifier = Modifier
                .requiredHeight(100.dp)
                .requiredWidth(100.dp)
                .padding(5.dp)

        )
    }

    //Root Checker app name Text
    @Composable
    fun TextHeader() {
        Text(
            text = "ROOT CHECKER",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.labelMedium
        )
    }

    //Root Info Header text Composable function
    @Composable
    fun TextHeaderInfo(mutableVal: String, mutableValCol: Int) {
        Text(
            text = mutableVal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color(mutableValCol)
        )
    }

    //Row component composable function for root related info
    @Composable
    fun RowComponentInCard(strDesc: String, mutableVal: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = strDesc,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = mutableVal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //Cancels the current coroutine scope and any running task on destroy
        cancel()
    }


    //Preview for jetpack composable view
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Material3AppTheme {
            MainViewImplementation()
        }
    }
}